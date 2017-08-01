package com.github.itonyli.demo;

import com.google.common.reflect.Reflection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class ReflectionDemo {

    public static void main(String[] args) {
        HelloService service = Reflection.newProxy(HelloService.class, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("--------------demo-----------------");
                if (method.getName().equals("hello")) {
                    HelloServiceImpl helloService = new HelloServiceImpl();
                    String value = helloService.hello(String.valueOf(args[0]));
                    System.out.println(value);
                }
                return null;
            }
        });

        service.hello("bb");
    }
}
