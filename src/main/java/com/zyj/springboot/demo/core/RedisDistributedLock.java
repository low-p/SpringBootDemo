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

package com.zyj.springboot.demo.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Redis分布式锁
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
@Component
@Service
public class RedisDistributedLock {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 释放锁的lua脚本
     */
    private static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * 加锁
     * @param key 。
     * @param value 。
     * @param expire 。
     * @return boolean
     */
    public boolean distributedLock(final String key, final String value, final long expire) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, value, "NX", "PX", expire);
            }
        });
        return !StringUtils.isEmpty(result);
    }

    /**
     * 移除过期时间(即永久有效)
     * @param key 。
     * @return boolean
     */
    public boolean persistKey(final String key){
        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.persist(key);
            }
        });
        return (null != result && result > 0);
    }

    /**
     *  释放锁
     * @param key 。
     * @param value 。
     * @return boolean
     */
    public boolean releaseLock(final String key, final String value) {
        List<String> keys = Collections.singletonList(key);
        List<String> args = Collections.singletonList(value);
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            }
        });
        return (null != result && result > 0);
    }

    /**
     * 获取过期时间
     * @param key 。
     * @return Long
     */
    public Long ttlKey(final String key){
        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.ttl(key);
            }
        });
        return result;
    }
}
