package com.zyj.springboot.demo.service.impl;

import com.whalin.MemCached.MemCachedClient;
import com.zyj.springboot.demo.core.config.UserSSOConfig;
import com.zyj.springboot.demo.core.sso.SSOConstant;
import com.zyj.springboot.demo.core.sso.SsoCookie;
import com.zyj.springboot.demo.dao.IUserDao;
import com.zyj.springboot.demo.entity.User;
import com.zyj.springboot.demo.service.IUserService;
import com.zyj.springboot.demo.util.SsoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zyj.springboot.demo.core.sso.SSOConstant.LOGIN_SESSION;

@Service
public class UserServiceImpl implements IUserService {
    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private IUserDao userDao;
    @Resource
    private UserSSOConfig userSSOConfig;
    @Autowired
    private MemCachedClient memCachedClient;

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

    @Override
    public void saveUserInfo(User user, HttpServletRequest request, HttpServletResponse response) {
        String userId = user.getIdString(), username = user.getUsername();
        request.getSession().setAttribute(LOGIN_SESSION, user);
        if (!userSSOConfig.isRepeatLogin()) {
            //不允许重复登录   踢掉在线的人
            String oldCookie = (String) memCachedClient.get(userId);
            logger.info("不允许重复登录, 清除已在线用户>>>>{} 的缓存>>>>{}", username, oldCookie);
            if (StringUtils.isNotBlank(oldCookie)) {
                memCachedClient.delete(oldCookie);
                memCachedClient.delete(userId);
            }
        }
        String token = SsoUtils.getUUID();
        // 以cookie的token值为key, userId值为Value, 放入缓存   设置30分钟过期
        memCachedClient.set(token, userId, new Date(1000*60*30));
        // 以cookie的token值为key, userId值为Value, 放入缓存   设置30分钟过期
        memCachedClient.set(userId, token, new Date(1000*60*30));
        logger.info("添加登录用户>>>>{} 的缓存>>>>{}", username, token);
        // 保存cookie的token值
        SsoCookie.setCookie(token, response);
    }
}
