package com.zyj.springboot.demo.service;

import com.zyj.springboot.demo.entity.User;

public interface IUserService {

    int insertUser(String username, String password, String nickname);

    User queryById(Integer id);

    User queryUser(String username, String password);
}
