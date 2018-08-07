package com.zyj.springboot.demo.service.impl;

import com.zyj.springboot.demo.service.IPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class PublisherServiceImpl implements IPublisherService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CountDownLatch countDownLatch;

    @Override
    public String publishMsg(String id) {
        // 第一种测试
        System.out.println("我要开始发送消息啦>>>>>>>>>>>>>>>>>>");
        stringRedisTemplate.convertAndSend("msg", "欢迎使用Redis消息队列");
        try {
            System.out.println("正在发送消息>>>>>>>>>>>>>>>>>>");
            countDownLatch.await(2000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("发送消息失败>>>>>>>>>>>>>>>>>>");
        }
        return "success";
        // 第二种测试
        /*if ("1".equals(id)) {
            stringRedisTemplate.convertAndSend("msg", "13636666666");
            System.out.println("Publisher  Sendes  Topic>>>>>>>>>>>>>>>>>>>");
            return "success";
        }
        return "failure";*/
    }
}
