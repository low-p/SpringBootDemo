package com.zyj.springboot.demo.core;

import com.zyj.springboot.demo.core.cache.*;
import com.zyj.springboot.demo.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Service
public class DBCacheAspect {
    public static final Logger logger = LoggerFactory.getLogger(DBCacheAspect.class);
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 定义拦截规则，拦截所有@QueryCache注解方法(切入点)
     */
    @Pointcut("@annotation(com.zyj.springboot.demo.core.cache.QueryCache)")
    public void queryCachePointcut(){}

    /**
     * 查询缓存拦截器逻辑实现
     * @param pjp  用于环绕通知，使用proceed()方法来执行目标方法
     * @return
     * @throws Throwable
     */
    @Around("queryCachePointcut()")
    public Object queryCacheInterceptor(ProceedingJoinPoint pjp) throws Throwable{
        Long startTime = System.currentTimeMillis();
        logger.info(">>>>>>Query--AOP--查询缓存切面--START>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 获取拦截方法
        Method method = signature.getMethod();
        // 获取@Querycache注解的nameSpace
        CacheNameSpace cacheName = method.getAnnotation(QueryCache.class).nameSpace();
        String key = null;
        int i = 0;
        // 循环所有参数
        for (Object arg : pjp.getArgs()) {
            MethodParameter methodParam = new SynthesizingMethodParameter(method, i);
            Annotation[] paramAnns = methodParam.getParameterAnnotations();
            // 循环参数上所有注解
            for (Annotation paramAnn : paramAnns) {
                if (paramAnn instanceof QueryCacheKey) {
                    //QueryCacheKey requestParam = (QueryCacheKey) paramAnn;
                    // 取到QueryCacheKey的标识参数的值
                    if (null == key) key = cacheName.name();
                    if (null != arg) key += "_" + arg;
                }
            }
            i++;
        }
        if (StringUtils.isBlank(key)) throw new RuntimeException("Query--缓存key值不存在");
        logger.info(">>>>>>Query--获取到缓存中key值>>>>>>" + key);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            // 从缓存中获取数据返回
            Object object = valueOperations.get(key);
            logger.info(">>>>>>Query--缓存获取数据>>>>>>" + JsonUtils.objectToJson(object));
            logger.info(">>>>>>Query--AOP--查询缓存切面--END--耗时>>>>>>" + (System.currentTimeMillis() - startTime) + "");
            return object;
        }
        // 如果缓存中没有数据，调用原始方法查询数据库
        Object object = pjp.proceed();
        if (null != object) {
            valueOperations.set(key, object, 30, TimeUnit.MINUTES);
            logger.info(">>>>>>Query--DB获取数据并存入缓存>>>>>>" + JsonUtils.objectToJson(object));
        }
        logger.info(">>>>>>Query--AOP--查询缓存切面--END--耗时>>>>>>" + (System.currentTimeMillis() - startTime) + "");
        return object;
    }
}
