package com.tydic.sps;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * User: chengjie
 * Date: 12-12-26
 * Time: 上午10:14
 */
public class HttpSnoopServer {
    private final int port;
    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(HttpSnoopServer.class.getName());
    public HttpSnoopServer(int port) {
        this.port = port;
    }

    public void run() {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new HttpSnoopServerPipelineFactory());
        logger.info("start server.................");
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));
    }

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new HttpSnoopServer(port).run();
        logger.info("server started");
    }

}
