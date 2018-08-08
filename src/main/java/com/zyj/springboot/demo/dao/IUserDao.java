package com.zyj.springboot.demo.dao;

import com.zyj.springboot.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IUserDao {
    User queryById(@Param(value = "id") Integer id);

    User queryByUser(Map<String, Object> paramMap);

    int insertUser(User user);

    int updateUser(User user);
}
