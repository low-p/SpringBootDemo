package com.zyj.springboot.demo.core.config;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class MemcachedConfig {

    @Value("${memcached.servers}")
    private String[] servers;   //设置memcached服务器地址
    @Value("${memcached.weights}")
    private Integer[] weights;  //设置每个MemCached服务器权重
    @Value("${memcached.failover}")
    private boolean failover;   //当一个memcached服务器失效的时候是否去连接另一个memcached服务器.
    @Value("${memcached.initConn}")
    private int initConn;   //初始化时对每个服务器建立的连接数目
    @Value("${memcached.minConn}")
    private int minConn;    //每个服务器建立最小的连接数
    @Value("${memcached.maxConn}")
    private int maxConn;    //每个服务器建立最大的连接数
    @Value("${memcached.maintSleep}")
    private int maintSleep; //自查线程周期进行工作，其每次休眠时间
    @Value("${memcached.nagle}")
    private boolean nagle;  //Socket的参数，如果是true在写数据时不缓冲，立即发送出去。Tcp的规则是在发送一个包之前，包的发送方会等待远程接收方确认已收到上一次发送过来的包；这个方法就可以关闭套接字的缓存——包准备立即发出。
    @Value("${memcached.socketTO}")
    private int socketTO;   //Socket阻塞读取数据的超时时间
    @Value("${memcached.aliveCheck}")
    private boolean aliveCheck; //设置是否检查memcached服务器是否失效
    @Value("${memcached.maxIdle}")
    private int maxIdle;    // 设置最大处理时间
    @Value("${memcached.socketConnectTO}")
    private int socketConnectTO;    //连接建立时对超时的控制

    @Bean
    public SockIOPool sockIOPool(){
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setWeights(weights);
        pool.setFailover(failover);
        pool.setInitConn(initConn);
        pool.setMinConn(minConn);
        pool.setMaxConn(maxConn);
        pool.setMaintSleep(maintSleep);
        pool.setNagle(nagle);
        pool.setSocketTO(socketTO);
        pool.setAliveCheck(aliveCheck);
        pool.setSocketConnectTO(socketConnectTO);
        pool.initialize();  //初始化连接池
        return pool;
    }

    @Bean
    public MemCachedClient memCachedClient(){
        MemCachedClient memCachedClient = new MemCachedClient();
        memCachedClient.setPrimitiveAsString(true); //是否将基本类型转换为String方法
        return memCachedClient;
    }

}
