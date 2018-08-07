package com.zyj.springboot.demo.controller;

import com.zyj.springboot.demo.service.IPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PublisherController
 * @Description:  消息队列测试控制层
 * @author: orange
 * @date: 2018/8/7 15:06
 */
@RestController
public class PublisherController {

    @Autowired
    private IPublisherService publisherService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "publish/{id}")
    public String pubMsg(@PathVariable String id){

        return publisherService.publishMsg(id);
    }

    @RequestMapping(value = "pub")
    public String testPublishMsg(){
        System.out.println("开始发送消息>>>>>>>>>>");
        stringRedisTemplate.convertAndSend("msg", "TestQueueMessageSuccess");
        return "success";
    }
}
