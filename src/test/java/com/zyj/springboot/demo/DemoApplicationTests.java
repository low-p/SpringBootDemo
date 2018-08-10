package com.zyj.springboot.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DemoApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void helloTest() {
        String body = testRestTemplate.getForObject("/test/hello", String.class);
        Assert.assertEquals("index", body);
    }

}
