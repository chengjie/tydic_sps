package com.tydic.sps.http;

import com.tydic.sps.document.action.Home;
import com.tydic.sps.util.HttpUtil;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

import java.lang.reflect.Method;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.setContentLength;
import static org.jboss.netty.handler.codec.http.HttpMethod.GET;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-16
 * Time: 下午8:48
 * To change this template use File | Settings | File Templates.
 */
public class HandleHttpRequest {
    public static void handleHttpRequest(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        HttpRequest req = (HttpRequest) e.getMessage();
        if (req.getMethod() != GET) {
            HttpUtil.sendHttpResponse(ctx, req, new DefaultHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }
        // Send the demo page and favicon.ico
        if (req.getUri().equals("/")||req.getUri().contains(".")) {
            HttpStaticResources.httpStaticResources(ctx,  e);
        } else {
            String[] uri = req.getUri().substring(1).split("/");
            if (uri.length == 2) {
                HttpResponse res = new DefaultHttpResponse(HTTP_1_1, OK);
                try {
                    //反射访问方法
                    Class<?> newClass = Class.forName("com.tydic.sps.document.action." + uri[0]);
                    Method method = newClass.getMethod(uri[1]);
                    ChannelBuffer content = (ChannelBuffer) method.invoke(newClass);
                    res.setHeader(CONTENT_TYPE, "text/html; charset=UTF-8");
                    setContentLength(res, content.readableBytes());
                    res.setContent(content);
                    HttpUtil.sendHttpResponse(ctx, req, res);
                } catch (Exception ex) {
                    //返回404
                    HttpUtil.sendHttpResponse(ctx, req, new DefaultHttpResponse(HTTP_1_1, NOT_FOUND));
                }
            } else {
                //返回404
                HttpUtil.sendHttpResponse(ctx, req, new DefaultHttpResponse(HTTP_1_1, NOT_FOUND));
            }
            return;
        }
    }


}
