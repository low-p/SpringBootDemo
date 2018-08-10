package com.zyj.springboot.demo.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class UserSSOConfig {

    // 是否允许重复登录
    @Value("${sso.repeatLogin}")
    private boolean repeatLogin;

    public boolean isRepeatLogin() {
        return repeatLogin;
    }

}
