package com.zyj.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserLoginController {

    @RequestMapping(value = "/")
    public String home (){

        return "login";
    }

    @RequestMapping(value = "/index")
    public String index (){

        return "index";
    }



}
