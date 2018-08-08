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
    public String publishMsg(String id) {
        // 第一种测试
        logger.info("我要开始发送消息啦>>>>>>>>>>>>>>>>>>");
        stringRedisTemplate.convertAndSend("msg", "欢迎使用Redis消息队列");
        try {
            logger.info("正在发送消息>>>>>>>>>>>>>>>>>>");
            countDownLatch.await(2000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("发送消息失败>>>>>>>>>>>>>>>>>>");
        }
        return "success";
        // 第二种测试
        /*if ("1".equals(id)) {
            stringRedisTemplate.convertAndSend("msg", "13636666666");
            logger.info("Publisher  Sendes  Topic>>>>>>>>>>>>>>>>>>>");
            return "success";
        }
        return "failure";*/
    }
}
