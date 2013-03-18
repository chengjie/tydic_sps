package com.tydic.sps.appserver;

import com.tydic.sps.domain.User;
import com.tydic.sps.mapper.UserMapper;
import com.tydic.sps.service.UserService;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        try {
            UserMapper userMapper = (UserMapper) ctx.getBean("userMapper");
            User user = userMapper.findUserById(1);
            System.out.println(user.getUsername());
            UserService userService = (UserService) ctx.getBean("userService");
            userService.findUserById(1);
            System.out.println(user.getUsername());
        } catch (Exception e) {
            System.out.println(e);
        }
        new AppServer(port).run();
    }
}
