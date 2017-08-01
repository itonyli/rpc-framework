package com.github.itonyli.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class ProviderRegister {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
        HelloService service = context.getBean("helloService", HelloService.class);
        String value = service.hello("tony");
        System.out.println(value);
        TimeUnit.MINUTES.sleep(5L);
    }
}
