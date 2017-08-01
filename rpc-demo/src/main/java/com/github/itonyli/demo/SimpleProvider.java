package com.github.itonyli.demo;

import com.github.itonyli.provider.RemoteServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class SimpleProvider {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring-provider.xml");
        new RemoteServer().start();
        TimeUnit.MINUTES.sleep(5L);
    }
}
