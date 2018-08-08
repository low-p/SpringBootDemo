package com.zyj.springboot.demo;

import com.whalin.MemCached.MemCachedClient;
import com.zyj.springboot.demo.util.MemcachedUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemcachedTest {
    public static final Logger logger = LoggerFactory.getLogger(MemcachedTest.class);
    @Autowired
    private MemCachedClient memCachedClient;

    @Test
    public void memcachedUtilsTest() throws InterruptedException {
        boolean flag;
        flag = MemcachedUtils.set("hello", "测试Memcached的Set方法");
        logger.info("Utils>>>>>>>>>>>状态: " + flag + ">>>>值: " + MemcachedUtils.get("hello"));
        MemcachedUtils.replace("hello", "测试Memcached的Replace方法");
        logger.info("Utils>>>>>>>>>>>获取值: " + MemcachedUtils.get("hello"));
        MemcachedUtils.set("hello_time", "测试Memcached的Set过期时间方法", new Date(3000));
        logger.info("Utils>>>>>>>>>>>获取值: " + MemcachedUtils.get("hello_time"));
        Thread.sleep(3000);
        logger.info("Utils>>>>>>>>>>>获取值: " + MemcachedUtils.get("hello_time"));
        MemcachedUtils.delete("hello");
        logger.info("Utils>>>>>>>>>>>获取值: " + MemcachedUtils.get("hello"));
    }

    @Test
    public void memcachedAnnotationTest() throws InterruptedException {
        boolean flag;
        flag = memCachedClient.set("hello", "测试Memcached的Set方法");
        logger.info("annotation>>>>>>>>>>>状态: " + flag + ">>>>值: " + memCachedClient.get("hello"));
        memCachedClient.replace("hello", "测试Memcached的Replace方法");
        logger.info("annotation>>>>>>>>>>>获取值: " + memCachedClient.get("hello"));
        memCachedClient.set("hello_time", "测试Memcached的Set过期时间方法", new Date(3000));
        logger.info("annotation>>>>>>>>>>>获取值: " + memCachedClient.get("hello_time"));
        Thread.sleep(3000);
        logger.info("annotation>>>>>>>>>>>获取值: " + memCachedClient.get("hello_time"));
        memCachedClient.delete("hello");
        logger.info("annotation>>>>>>>>>>>获取值: " + memCachedClient.get("hello"));
    }
}
