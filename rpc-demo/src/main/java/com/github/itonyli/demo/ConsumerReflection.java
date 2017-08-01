package com.github.itonyli.demo;


import com.github.itonyli.common.entity.URL;
import com.github.itonyli.consumer.proxy.ServiceProxy;
import com.google.common.reflect.Reflection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class ConsumerReflection {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
        URL url = new URL();
        url.setAppName("demo");
        url.setServiceName("hello");
        HelloService service = Reflection.newProxy(HelloService.class, new ServiceProxy(url));
        service.hello("AA");
        TimeUnit.MINUTES.sleep(5L);
    }
}
