package com.zyj.springboot.demo.core;

import com.zyj.springboot.demo.core.queue.Receiver;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: PublisherConfig
 * @Description:  队列发布者以及订阅者配置
 * @author: orange
 * @date: 2018/8/7 14:50
 */
@Configuration
@EnableAutoConfiguration
public class PublisherConfig {

    /**
     * @Title: template
     * @Description:  redis的模板  作为发布者
     * @params [connectionFactory]
     * @return  org.springframework.data.redis.core.StringRedisTemplate    返回类型
     * @throws
     */
    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    /**
     * @Title: receiver
     * @Description:  注册订阅者
     * @params [latch]
     * @return  com.zyj.springboot.demo.core.queue.Receiver    返回类型
     * @throws
     */
    @Bean
    public Receiver receiver(CountDownLatch latch){

        return new Receiver(latch);
    }

    /**
     * @Title: latch
     * @Description:  计数器，控制线程
     * @return  java.util.concurrent.CountDownLatch    返回类型
     * @throws
     */
    @Bean
    public CountDownLatch latch () {
        return new CountDownLatch(1);
    }

    /**
     * @Title: container
     * @Description:  创建连接工厂
     * @params [connectionFactory, listenerAdapter]
     * @return  org.springframework.data.redis.listener.RedisMessageListenerContainer    返回类型
     * @throws
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("msg"));
        return container;
    }

    /**
     * @Title: listenerAdapter
     * @Description:  绑定消息监听者和接收监听的方法
     * @params [receiver]
     * @return  org.springframework.data.redis.listener.adapter.MessageListenerAdapter    返回类型
     * @throws
     */
    @Bean
    public MessageListenerAdapter listenerAdapter (Receiver receiver){

        return new MessageListenerAdapter(receiver, "receiveMsg");
    }
}
