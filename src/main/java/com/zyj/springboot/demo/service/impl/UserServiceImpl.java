package com.zyj.springboot.demo.service.impl;

import com.zyj.springboot.demo.dao.IUserDao;
import com.zyj.springboot.demo.entity.User;
import com.zyj.springboot.demo.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;

    @Override
    public int insertUser(String username, String password, String nickname) {
        User user = this.initUserInfo(username, password, nickname);
        if (null != user) {
            return userDao.insertUser(user);
        }
        return 0;
    }

    /**
     * @Title: initUserInfo
     * @Description:  user信息初始化验证
     * @params [username, password, nickname]
     * @return  com.zyj.springboot.demo.entity.User    返回类型
     * @throws
     */
    private User initUserInfo(String username, String password, String nickname) {
        if(StringUtils.isEmpty(username)) throw new RuntimeException("用户名为空");
        if(StringUtils.isEmpty(password)) throw new RuntimeException("用户密码为空");
        if(StringUtils.isEmpty(nickname)) throw new RuntimeException("用户昵称为空");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        return user;
    }


    @Override
    public User queryById(Integer id) {
        if (null != id) {
            return userDao.queryById(id);
        }
        return null;
    }

    @Override
    public User queryUser(String username, String password) {
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("username", username);
            paramMap.put("password", password);
            return userDao.queryByUser(paramMap);
        }
        return null;
    }
}
