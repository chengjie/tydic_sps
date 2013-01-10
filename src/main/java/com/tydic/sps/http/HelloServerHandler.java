package com.tydic.sps.http;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

/**
 * User: chengjie
 * Date: 12-12-26
 * Time: 上午10:26
 */
public class HelloServerHandler extends SimpleChannelHandler {
    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(HelloServerHandler.class.getName());

    /**
     * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
     *
     * @alia OneCoder
     * @author lihzh
     */
    @Override
    public void channelConnected(
            ChannelHandlerContext ctx,
            ChannelStateEvent e) {

        logger.info("有客户端连接:" + ctx.getChannel().getId().toString());

        Channel ch = e.getChannel();
        ChannelBuffer time = ChannelBuffers.buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        ChannelFuture f = ch.write(time);
        //关闭连接
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelUnbound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("停止连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.info("客户端" + ctx.getChannel().getId() + "断开了");
        e.getChannel().close();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer buf = (ChannelBuffer) e.getMessage();
        Channel ch = e.getChannel();
        ch.write(e.getMessage());
        while (buf.readable()) {
            System.out.println((char) buf.readByte());
        }
    }
}
