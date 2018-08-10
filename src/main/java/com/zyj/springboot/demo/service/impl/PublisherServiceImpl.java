package com.zyj.springboot.demo.service.impl;

import com.zyj.springboot.demo.service.IPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class PublisherServiceImpl implements IPublisherService {
    public static final Logger logger = LoggerFactory.getLogger(PublisherServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CountDownLatch countDownLatch;

    @Override
    public String publishMsg(String key) {
        // 第一种写法   默认发送
        if ("test".equals(key)) {
            logger.info("FirstMethod----我要开始发送消息啦>>>>>>>>>>>>>>>>>>");
            stringRedisTemplate.convertAndSend("msg", "WelcomeUseRedisQueueMessage");
            logger.info("FirstMethod----正在发送消息>>>>>>>>>>>>>>>>>>");
            return "success";
        }
        return "failure";
    }

    @Override
    public String testPubMsg() {
        logger.info("SecondMethod----我要开始发送消息啦>>>>>>>>>>>>>>>>>>");
        // 第二种写法   指定延迟时间发送
        stringRedisTemplate.convertAndSend("msg", "欢迎使用Redis消息队列");
        try {
            logger.info("SecondMethod----正在发送消息>>>>>>>>>>>>>>>>>>");
            countDownLatch.await(2000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("SecondMethod----发送消息失败>>>>>>>>>>>>>>>>>>");
            return "failure";
        }
        return "success";
    }
}
