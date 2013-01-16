package com.tydic.sps.appserver;

import com.tydic.sps.document.action.Home;
import com.tydic.sps.http.HandleHttpRequest;
import com.tydic.sps.socket.HandleWebSocketFrame;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.*;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.CharsetUtil;

import java.lang.reflect.Method;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.HOST;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.setContentLength;
import static org.jboss.netty.handler.codec.http.HttpMethod.GET;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-10
 * Time: 下午2:45
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {

    private static final String WEBSOCKET_PATH = "/websocket";

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

    private static String getWebSocketLocation(HttpRequest req) {
        return "ws://" + req.getHeader(HOST) + WEBSOCKET_PATH;
    }


}
