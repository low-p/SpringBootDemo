package com.zyj.springboot.demo.controller;

import com.zyj.springboot.demo.service.IPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    @Autowired
    private IPublisherService publisherService;

    @RequestMapping(value = "publish/{key}")
    public String pubMsg(@PathVariable String key){

        return publisherService.publishMsg(key);
    }

    @RequestMapping(value = "publish")
    public String testPublishMsg(){

        return publisherService.testPubMsg();
    }
}
