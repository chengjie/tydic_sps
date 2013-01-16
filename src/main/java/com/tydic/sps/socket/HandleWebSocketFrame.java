package com.tydic.sps.socket;

import com.tydic.sps.util.HttpUtil;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.*;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.HOST;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-16
 * Time: 下午8:55
 * To change this template use File | Settings | File Templates.
 */
public class HandleWebSocketFrame {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HandleWebSocketFrame.class);

    private static WebSocketServerHandshaker handshaker;

    /**
     * soket请求
     *
     * @param ctx
     * @param e
     */
    public static void handleWebSocketFrame(ChannelHandlerContext ctx, MessageEvent e) {
        WebSocketFrame frame = (WebSocketFrame) e.getMessage();
        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.getChannel(), (CloseWebSocketFrame) frame);
            return;
        } else if (frame instanceof PingWebSocketFrame) {
            ctx.getChannel().write(new PongWebSocketFrame(frame.getBinaryData()));
            return;
        } else if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass()
                    .getName()));
        }
        // Send the uppercase string back.
        String request = ((TextWebSocketFrame) frame).getText();
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Channel %s received %s", ctx.getChannel().getId(), request));
        }
        ctx.getChannel().write(new TextWebSocketFrame(request.toUpperCase()));
    }



}
