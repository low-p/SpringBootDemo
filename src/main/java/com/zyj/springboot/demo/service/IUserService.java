package com.zyj.springboot.demo.service;

import com.zyj.springboot.demo.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    int insertUser(String username, String password, String nickname);

    User queryById(Integer id);

    User queryUser(String username, String password);

    void saveUserInfo(User user, HttpServletRequest request, HttpServletResponse response);
}
