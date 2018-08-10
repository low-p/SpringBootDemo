package com.zyj.springboot.demo.core.sso;


import com.whalin.MemCached.MemCachedClient;
import com.zyj.springboot.demo.core.config.UserSSOConfig;
import com.zyj.springboot.demo.entity.User;
import com.zyj.springboot.demo.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zyj.springboot.demo.core.sso.SSOConstant.LOGIN_SESSION;

@Component
@ServletComponentScan
public class UserSessionFilter implements Filter {
    public static final Logger logger = LoggerFactory.getLogger(UserSessionFilter.class);

    private String prersonPassUrl = "css.*,ico.*,images.*,js.*,webjars.*,ws.*,index.*,login.*,test.*";

    private String[] prersonPassUrls;

    @Resource
    private UserSSOConfig userSSOConfig;

    @Autowired
    private IUserService userService;

    @Resource
    private MemCachedClient memCachedClient;

    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        prersonPassUrls = prersonPassUrl.split(",");
        for (String url : prersonPassUrls) {
            Pattern p = Pattern.compile(url);
            patterns.add(p);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        if (isInclude(url)) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        } else {
            logger.info("访问URL>>>>>>>>>>>>>>>>>>>>>>>" + url);
            HttpSession session = httpRequest.getSession();
            User user = (User) session.getAttribute(LOGIN_SESSION);
            if (userSSOConfig.isRepeatLogin() && user != null) {
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            } else {
                this.refreshSession(session, httpRequest, user);
                if (session.getAttribute(LOGIN_SESSION) == null){
                    logger.info("session刷新失败，跳转登录页");

                    httpResponse.sendRedirect("/demo/index");
                    return;
                }
                logger.info("session刷新成功");
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            }
        }
    }

    @Override
    public void destroy() {

    }
    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 刷新session
     * @param session
     * @param request
     */
    private void refreshSession(HttpSession session, HttpServletRequest request, User user){
        Cookie cookie = SsoCookie.getCookie(request);
        if (cookie != null){
            String cookieValue = cookie.getValue();
            if (StringUtils.isNotBlank(cookieValue)) {
                // 通过cookie值，获取缓存中用户的信息
                String userId = (String) memCachedClient.get(cookieValue);

                // cookie值为key对应的userId不存在 && userId为key对应的cookie存在，并且与之不相等
                if (StringUtils.isBlank(userId) && user != null) {
                    String memcachedCookie = (String) memCachedClient.get(user.getIdString());
                    if (StringUtils.isNotBlank(memcachedCookie) && !StringUtils.equals(memcachedCookie, cookieValue)){
                        session.setAttribute(LOGIN_SESSION, null);
                        logger.info("您已被踢下线，如不是您操作，请尽快修改密码。。。");
                        return;
                    }
                    /*else if (StringUtils.isBlank(userId)){
                        // 缓存时间超时自动过期，重新设置缓存值
                        String key =SsoUtils.saveMemcached(cookieValue, user.getIdString());
                        logger.info("缓存自动过期，从session中将信息刷会缓存：{},{}", user.getIdString(), key);
                    }*/
                }

                // 如果session中有值，直接跳过
                if (user != null) {
                    return;
                }

                // 如果缓存值有，session值过期，则从新获取user信息并设置session
                if (StringUtils.isNotBlank(userId)) {
                    // 通过缓存中存的userId来获取用户的详细信息。
                    user = userService.queryById(Integer.parseInt(userId));
                    session.setAttribute(LOGIN_SESSION, user);
                }
            }
        }
    }
}
