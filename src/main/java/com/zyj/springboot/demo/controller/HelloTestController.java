package com.zyj.springboot.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTestController {

    @RequestMapping(value="/hello")
    public String hello(){

        return "index";
    }

    @RequestMapping(value = "/test")
    public String testString(){

        return "success";
    }
}
