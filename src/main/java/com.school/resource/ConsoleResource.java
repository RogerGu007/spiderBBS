package com.school.resource;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsoleResource {

    public static void main(String[] args) {
        //start server
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        try {
            Server server = new Server(8090);
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
