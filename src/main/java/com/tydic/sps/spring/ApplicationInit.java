package com.tydic.sps.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-3-7
 * Time: 下午2:32
 */
public class ApplicationInit {

    public static ApplicationContext applicationContext = null;

    private ApplicationInit() {
    }

    public static void context() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        }
    }
}
