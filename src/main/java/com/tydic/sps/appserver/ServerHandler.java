package com.tydic.sps.appserver;

import com.tydic.sps.http.HandleHttpRequest;
import com.tydic.sps.socket.HandleWebSocketFrame;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.*;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.HOST;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-10
 * Time: 下午2:45
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {


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
}
