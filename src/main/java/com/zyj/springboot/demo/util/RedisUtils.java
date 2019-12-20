/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @since 0.0.1
 */

package com.zyj.springboot.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * <pre>
 * Redis分布式锁工具类
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class RedisUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final Long PERSIST_CONDITION = 0L;

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      客户端。
     * @param lockKey    锁。
     * @param lockValue  请求标识。
     * @param expireTime 超期时间。
     * @return boolean 是否获取成功
     */
    public static boolean distributedLock(final Jedis jedis, final String lockKey, final String lockValue, final long expireTime) {

        String result = jedis.set(lockKey, lockValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        logger.info("Jedis加锁结果>>>>>>>{}", result);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 移除key的过期时间
     *
     * @param jedis 客户端。
     * @param key   。
     * @return boolean
     */
    public static boolean persistKey(final Jedis jedis, final String key) {
        Long result = jedis.persist(key);
        logger.info("Jedis移除过期时间结果>>>>>>>{}", result);
        return null != result && result > PERSIST_CONDITION;
    }

    /**
     * 释放分布式锁
     *
     * @param jedis     客户端。
     * @param lockKey   锁。
     * @param lockValue 请求标识。
     * @return boolean 是否释放成功
     */
    public static boolean releaseLock(final Jedis jedis, final String lockKey, final String lockValue) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
        logger.info("Jedis释放锁结果>>>>>>>{}", result);
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 获取key的过期时间
     *
     * @param jedis 客户端。
     * @param key   。
     * @return boolean
     */
    public static boolean ttlKey(final Jedis jedis, final String key) {
        Long result = jedis.ttl(key);
        logger.info("Jedis获取过期时间结果>>>>>>>{}", result);
        return null != result && result > PERSIST_CONDITION;
    }
}
