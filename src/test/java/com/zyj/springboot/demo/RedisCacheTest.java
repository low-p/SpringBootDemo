package com.zyj.springboot.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheTest {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void cacheTest(){
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("hello", "Redis");
        System.out.println("RedisHello: " + valueOperations.get("hello"));
    }
}
