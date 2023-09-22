package com.example.springbootrestdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class HelloWorldResourse {

    @RequestMapping("/hello-world")

    public String helloWorld(){
        return "Hello World!!";
    }

    @RequestMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return  new HelloWorldBean("Hey!! this is the message from hello world");
    }

    @RequestMapping("/hello-world-path-param/{name}/message/{message}")
    public HelloWorldBean helloWorldPathParam(@PathVariable String name, @PathVariable String message){
        return new HelloWorldBean("helloworld"+" "+name+" "+ message);
    }
}
