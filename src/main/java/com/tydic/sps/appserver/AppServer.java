package com.tydic.sps.appserver;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Web socket服务器管理
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-10
 * Time: 下午2:42
 */
public class AppServer {
    private final int port;

    public AppServer(int port) {
        this.port = port;
    }

    public void run() {
        // 配置服务
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        // 设置 pipeline factory.
        bootstrap.setPipelineFactory(new ServerPipelineFactory());

        // 绑定连接
        bootstrap.bind(new InetSocketAddress(port));

        System.out.println("打开浏览器访问 http://localhost:" + port + '/');
    }

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new AppServer(port).run();
    }
}
