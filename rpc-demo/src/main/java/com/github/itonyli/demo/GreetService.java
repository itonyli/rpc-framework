package com.github.itonyli.demo;

import com.github.itonyli.common.annotation.Consumer;
import org.springframework.stereotype.Service;

@Service
public class GreetService {

    @Consumer(app = "demo", service = "hello")
    private HelloService helloService;
}
