package com.zyj.springboot.demo.controller;

import com.zyj.springboot.demo.service.impl.RedisDistributedLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTestController {
    @Autowired
    private RedisDistributedLockService redisDistributedLockService;

    @RequestMapping(value="/test/hello")
    public String hello(){
        redisDistributedLockService.testDistributedLock();
        return "index";
    }

    @RequestMapping(value = "/test/success")
    public String testString(){

        return "success";
    }
}
