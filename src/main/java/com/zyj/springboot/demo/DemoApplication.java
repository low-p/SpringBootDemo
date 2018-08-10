package com.zyj.springboot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.zyj.springboot.demo.dao")
public class DemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){

        return builder.sources(DemoApplication.class);
    }

    public static void main(String[] args) {
        // 启动应用run方法
        SpringApplication.run(DemoApplication.class, args);
    }
}
