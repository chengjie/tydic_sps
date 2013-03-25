package com.tydic.sps.appserver;

import com.tydic.sps.http.HandleHttpRequest;
import com.tydic.sps.socket.HandleWebSocketFrame;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMessage;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.*;

import java.util.List;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.HOST;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-10
 * Time: 下午2:45
 */
public class ServerHandler extends SimpleChannelHandler {

    private HttpMessage currentMessage;
    private long maxContentLength = 65536;
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Object msg = e.getMessage();
        if (msg instanceof HttpRequest) {
            HandleHttpRequest.handleHttpRequest(ctx, e);
        } else if (msg instanceof WebSocketFrame) {
            HandleWebSocketFrame.handleWebSocketFrame(ctx, e);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    public boolean excuteChunk(ChannelHandlerContext ctx, MessageEvent e)
            throws TooLongFrameException {
        // HttpMessage currentMessage = e.getMessage();
        if (e.getMessage() instanceof HttpMessage) {
            HttpMessage m = (HttpMessage) e.getMessage();
            if (m.isChunked()) {
                // A chunked message - remove 'Transfer-Encoding' header,
                // initialize the cumulative buffer, and wait for incoming
                // chunks.
                List<String> encodings = m
                        .getHeaders(HttpHeaders.Names.TRANSFER_ENCODING);
                encodings.remove(HttpHeaders.Values.CHUNKED);
                if (encodings.isEmpty()) {
                    m.removeHeader(HttpHeaders.Names.TRANSFER_ENCODING);
                }
                m.setContent(ChannelBuffers.dynamicBuffer(e.getChannel()
                        .getConfig().getBufferFactory()));
                this.currentMessage = m;
            } else {
                // Not a chunked message - pass through.
                this.currentMessage = null;
            }
            return false;
        } else if (e.getMessage() instanceof HttpChunk) {
            // Sanity check
            if (currentMessage == null) {
                throw new IllegalStateException("received "
                        + HttpChunk.class.getSimpleName() + " without "
                        + HttpMessage.class.getSimpleName());
            }
            // Merge the received chunk into the content of the current message.
            HttpChunk chunk = (HttpChunk) e.getMessage();
            ChannelBuffer content = currentMessage.getContent();
            if (content.readableBytes() > maxContentLength
                    - chunk.getContent().readableBytes()) {
                throw new TooLongFrameException("HTTP content length exceeded "
                        + maxContentLength + " bytes.");
            }
            content.writeBytes(chunk.getContent());
            if (chunk.isLast()) {
                this.currentMessage = null;
                currentMessage.setHeader(HttpHeaders.Names.CONTENT_LENGTH,
                        String.valueOf(content.readableBytes()));
                return true;
                // Channels.fireMessageReceived(ctx, currentMessage,
                // e.getRemoteAddress());
            }
        }
        return true;
    }

}
