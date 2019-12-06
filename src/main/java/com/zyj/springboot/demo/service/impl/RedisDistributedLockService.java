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

package com.zyj.springboot.demo.service.impl;

import com.zyj.springboot.demo.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * <pre>
 * 测试分布式锁
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
@Service
public class RedisDistributedLockService {
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLockService.class);
    @Autowired
    private Jedis jedis;

    public void testDistributedLock(){
        RedisUtils.distributedLock(jedis, "jedisExpireTime", "123456", 5*1000);
        logger.info("获取锁的过期时间>>>>>> {} ", RedisUtils.ttlKey(jedis, "jedisExpireTime"));
        boolean resultBefore = RedisUtils.distributedLock(jedis, "jedisExpireTime", "234567", 5*1000);
        logger.info("过期时间内加锁>>>>>> {} ", resultBefore);
        try {
            Thread.sleep(4*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("获取锁的过期时间>>>>>> {} ", RedisUtils.ttlKey(jedis, "jedisExpireTime"));
        boolean resultAfter = RedisUtils.distributedLock(jedis, "jedisExpireTime", "345678", 5*1000);
        logger.info("过期时间后加锁>>>>>> {} ", resultAfter);
        jedis.persist("jedisExpireTime");
    }
}
