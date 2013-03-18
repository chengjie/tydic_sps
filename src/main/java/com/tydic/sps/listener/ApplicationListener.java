package com.tydic.sps.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 应用侦听器
 *
 */
public class ApplicationListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationListener.class);

    private ApplicationContext contextLoader;


    /**
     * Initialize the root web application context.
     */
    public void contextInitialized() {
        this.contextLoader = createContextLoader();
    }

    /**
     * Create the ContextLoader to use. Can be overridden in subclasses.
     * @return the new ContextLoader
     */
    protected ApplicationContext createContextLoader() {
        return new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    /**
     * Return the ContextLoader used by this listener.
     * @return the current ContextLoader
     */
    public ApplicationContext getContextLoader() {
        return this.contextLoader;
    }


    /**
     * Close the root web application context.
     */
    public void contextDestroyed() {

    }
}
