package com.zyj.springboot.demo;

import com.zyj.springboot.demo.core.ResultPage;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.StudentInfoService;
import com.zyj.springboot.demo.util.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheTest {
    public static final Logger logger = LoggerFactory.getLogger(RedisCacheTest.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StudentInfoService studentInfoService;

    @Test
    public void cacheValueTest(){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //设置key为hello到Redis
        valueOperations.set("hello", "Hello Redis!!!", 1, TimeUnit.HOURS);
        StudentInfo info = new StudentInfo();
        info.setsId(1001);
        info.setsName("赵橘子");
        info.setsClass("三年四班");
        info.setSex("男");
        info.setAge(20);
        valueOperations.set("studentInfo", info, 1, TimeUnit.HOURS);
        logger.info(">>>>>>>>>>>>>ValueHello: " + valueOperations.get("hello"));
        Object obj = valueOperations.get("studentInfo");
        logger.info(">>>>>>>>>>>>>ValueStudent: " + JsonUtils.objectToJson(obj));
    }

    @Test
    public void cacheHashTest(){
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        Map<String, Object> appleMap = new HashMap<>();
        appleMap.put("sn", "201807260002");
        appleMap.put("name", "橘子");
        appleMap.put("type", "香橘");
        appleMap.put("area", "上海青浦");
        //appleMap的key和value全部插入appleHash中
        hashOperations.putAll("appleHash", appleMap);
        //单个插入appleHash中key和value值
        //hashOperations.put("appleHash", "sn", "201807260002");
        //hashOperations.put("appleHash", "name", "橘子");
        //hashOperations.put("appleHash", "type", "香橘");
        //hashOperations.put("appleHash", "area", "上海青浦");
        logger.info(">>>>>>>>>>>>>HashAplleName: " + hashOperations.get("appleHash", "name"));
    }

    @Test
    public void cacheeListTest(){
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<Object> list = new ArrayList<>();
        list.add("java");
        list.add("python");
        list.add("c#");
        list.add("c++");
        list.add("php");
        //从list集合中按左侧顺序加入元素到listObject集合
        listOperations.leftPushAll("listObject", list);
        //在listObject集合左侧加入元素scala
        //listOperations.leftPush("listObject", "scala");
        //在listObject集合中c++左侧加入元素javaScript
        //listOperations.leftPush("listObject", "c++", "javaScript");
        //在listObject集合中c++右侧加入元素ruby
        //listOperations.rightPush("listObject", "c++", "ruby");
        logger.info(">>>>>>>>>>>>>ListString: " + listOperations.range("listObject", 0, -1));
    }

    @Test
    public void cacheSetTest(){
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        //languageSet无序集合加入多个元素
        setOperations.add("languageSet", "Java", "Python", "C++", "Scala");
        setOperations.add("languageSet", "Rudy");

        //languageSet2无序集合加入多个元素
        setOperations.add("languageSet2", "JavaScript", "Python", "Java", "C#");

        // 求languageSet和languageSet2交集存入languageSet3
        setOperations.intersectAndStore("languageSet", "languageSet2", "languageSet3");

        // 求languageSet和languageSet2并集存入languageSet4
        setOperations.unionAndStore("languageSet", "languageSet2", "languageSet4");

        logger.info(">>>>>>>>>>>>>Set-languageSet:" + setOperations.members("languageSet"));
        logger.info(">>>>>>>>>>>>>Set-languageSet2:" + setOperations.members("languageSet2"));
        logger.info(">>>>>>>>>>>>>Set-languageSet3:" + setOperations.members("languageSet3"));
        logger.info(">>>>>>>>>>>>>Set-languageSet4:" + setOperations.members("languageSet4"));
    }

    @Test
    public void cacheZsetTest(){
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        //单个插入Set有序集合
        zSetOperations.add("stringZset", "zset-1", 4);
        zSetOperations.add("stringZset", "zset-2", 3);
        zSetOperations.add("stringZset", "zset-3", 2);
        zSetOperations.add("stringZset", "zset-4", 1);

        //批量插入Set有序集合
        ZSetOperations.TypedTuple<Object> typedTuple = new DefaultTypedTuple<>("zset-5", 3.2);
        ZSetOperations.TypedTuple<Object> typedTuple1 = new DefaultTypedTuple<>("zset-6", 1.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        tuples.add(typedTuple);
        tuples.add(typedTuple1);
        zSetOperations.add("stringZset", tuples);

        logger.info(">>>>>>>>>>>>>Zset-stringZset:" + zSetOperations.range("stringZset", 0, -1));
        //logger.info(">>>>>>>>>>>>>Zset-stringZset:" + zSetOperations.incrementScore("stringZset", "zset-1", 1.1));
        //logger.info(">>>>>>>>>>>>>Zset-stringZset:" + zSetOperations.rangeByScore("stringZset",0, 4, 1, 2));
    }

    @Test
    public void cacheAOPTest(){
        StudentInfo info = studentInfoService.findStudentById(12);
        logger.info(">>>>>>>>>>>>>StudentInfo: " + info.toString());
        ResultPage pageInfo = studentInfoService.queryStudentList(1, 5, "小");
        logger.info(">>>>>>>>>>>>>StudentList: " + JsonUtils.objectToJson(pageInfo));

    }

}
