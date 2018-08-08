package com.zyj.springboot.demo;

import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.entity.User;
import com.zyj.springboot.demo.service.IStudentInfoService;
import com.zyj.springboot.demo.service.IUserService;
import com.zyj.springboot.demo.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DemoApplicationTests {
    public static final Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private IStudentInfoService studentInfoService;
    @Autowired
    private IUserService userService;

    @Test
    public void helloTest() {
        String body = testRestTemplate.getForObject("/hello", String.class);
        Assert.assertEquals("index", body);
    }

    @Test
    public void addStudentTest(){
        StudentInfo student = new StudentInfo();
        student.setsName("胡小菲");
        student.setsClass("三年一班");
        student.setSex("女");
        student.setAge(20);
        StudentInfo info = studentInfoService.insertStudent(student);
        logger.info("StudentInfo: " + JsonUtils.objectToJson(info));
    }

    @Test
    public void insertUserTest(){
        String username = "admin", password = "admin", nickname = "adm";
        int res = userService.insertUser(username, password, nickname);
        logger.info(">>>>>>>返回值：" + res);
    }

    @Test
    public void queryUserTest(){
        User user = userService.queryById(1);
        logger.info(">>>>>>>>>>返回User对象：" + JsonUtils.objectToJson(user));
        String username = "admin", password = "admin";
        user = userService.queryUser(username, password);
        logger.info(">>>>>>>>>>返回User对象：" + JsonUtils.objectToJson(user));
    }
}
