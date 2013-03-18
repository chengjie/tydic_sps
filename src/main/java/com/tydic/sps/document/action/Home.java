package com.tydic.sps.document.action;

import com.tydic.sps.appserver.WebSocketServerIndexPage;
import com.tydic.sps.mapper.UserMapper;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-13
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
public class Home {

    public static ChannelBuffer home() {
        /*return ChannelBuffers.copiedBuffer("" +
                "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" ><title>接口列表</title></head><body>" +
                "<article>" +
                "<section>" + "</section>"+
                "</article>" +
                "<header><h2>接口列表</h2></header>" +
                "</body></html>"
                , CharsetUtil.UTF_8);*/
        return WebSocketServerIndexPage.getContent("ws://localhost:8080/websocket/web");
    }

    public static ChannelBuffer hello() {
        InputStream fileInputStream = null;
        try {
            fileInputStream = ClassLoader.getSystemResourceAsStream("WEB/default.css");
            for (; ; ) {
                BufferedInputStream bis = new BufferedInputStream(fileInputStream);
                byte[] bytes = new byte[50];
                int readNum = 0;
                try {
                    readNum = bis.read(bytes, 0, 50);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if (readNum == -1) {
                    return ChannelBuffers.copiedBuffer("404", CharsetUtil.UTF_8);
                }
                return ChannelBuffers.copiedBuffer(bytes, 0, readNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ChannelBuffers.copiedBuffer("404", CharsetUtil.UTF_8);
    }
}