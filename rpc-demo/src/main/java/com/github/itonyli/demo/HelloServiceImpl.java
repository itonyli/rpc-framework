package com.github.itonyli.demo;

import com.github.itonyli.common.annotation.Provider;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Provider(app = "demo", service = "hello")
    public String hello(String name) {
        return "hello " + name + "!";
    }
}
