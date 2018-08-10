package com.zyj.springboot.demo.controller;

import com.zyj.springboot.demo.entity.User;
import com.zyj.springboot.demo.service.IUserService;
import com.zyj.springboot.demo.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserLoginController {
    public static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/")
    public String home (){

        return "index";
    }

    @RequestMapping(value = "/index")
    public String index (){

        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, String username, String password){
        logger.info("进入登录方法>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            logger.info("请求参数>>>>>>" + username + ">>>>>>" + password);
            User user = userService.queryUser(username, password);
            if (null != user) {
                userService.saveUserInfo(user, request, response);
                return "redirect:/student/home";
            }
        }
        return "index";
    }

}
