package com.example.springbootrestdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloWorldResourse {

    @RequestMapping("/hello-world")
    @ResponseBody
    public String helloWorld(){
        return "Hello World!!";
    }
}
