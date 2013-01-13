package com.tydic.sps.action;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-13
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
public class Home {
    public static ChannelBuffer home(){
        return ChannelBuffers.copiedBuffer("" +
                "<html><head></head><body>Hello World</body></html>"
                , CharsetUtil.US_ASCII);
    }
}
