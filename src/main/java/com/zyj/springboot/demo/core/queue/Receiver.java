package com.zyj.springboot.demo.core.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: Receiver
 * @Description:  接受消息者(订阅者)
 * @author: orange
 * @date: 2018/8/7 15:09
 */
public class Receiver {
    public static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private CountDownLatch latch;

    @Autowired
    public Receiver (CountDownLatch latch){
        this.latch = latch;
    }

    public void receiveMsg(String message){
        logger.info("ReceiveMessage>>>>>>>>>>>>>>>>>>[" + message + "]");
        latch.countDown();
    }
}
