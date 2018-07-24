package com.zyj.springboot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyj.springboot.demo.dao")
public class DemoApplication {

    public static void main(String[] args) {
        // 启动应用run方法
        SpringApplication.run(DemoApplication.class, args);
    }
}
