package com.zyj.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloTestController {

    @RequestMapping(value="/hello", method=RequestMethod.GET)
    public String hello(){

        return "index";
    }
}
