package com.github.itonyli.demo;


import com.github.itonyli.common.entity.URL;
import com.github.itonyli.consumer.proxy.ServiceProxy;
import com.google.common.reflect.Reflection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class SimpleConsumer {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring-consumer.xml");
        URL url = new URL();
        url.setAppName("demo");
        url.setServiceName("hello");
        for (int i = 0; i < 10; i++) {
            long current = System.currentTimeMillis();
            HelloService service = Reflection.newProxy(HelloService.class, new ServiceProxy(url));
            System.out.println(service.hello("tony" + i) + ", call spend " + String.valueOf(System.currentTimeMillis() - current) + "ms");
        }
        TimeUnit.MINUTES.sleep(5L);
    }
}
