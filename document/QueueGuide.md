                            Springboot整合Redis实现消息队列DEMO                               
1、引入Redis依赖包    
   
   `<dependency>      
        <groupId>org.springframework.boot</groupId>            
        <artifactId>spring-boot-starter-data-redis</artifactId>           
    </dependency>`    
   
2、配置redis并确保redis服务启动----redis示例配置如下           
   
    #redis基础配置
       redis:
           # Redis数据库索引（默认为0）
           database: 0
           host: 127.0.0.1
           port: 6379
           # 连接超时时间（毫秒）
           timeout: 1000ms
           password: 123456
           jedis:
             pool:
               # 连接池最大连接数（使用负值表示没有限制）
               max-active: 8
               # 连接池中的最小空闲连接
               min-idle: 0
               # 连接池中的最大空闲连接
               max-idle: 8
               # 连接池最大阻塞等待时间（使用负值表示没有限制）
               max-wait: -1ms
   
3、创建一个消息接收者(订阅者)      
    
    /**
     * @ClassName: Receiver
     * @Description:  接受消息者(订阅者)
     * @author: orange
     * @date: 2018/8/7 15:09
     */
    public class Receiver {  
    
       private CountDownLatch latch;    
       
       @Autowired    
       public Receiver (CountDownLatch latch){    
           this.latch = latch;    
       }   
       
       public void receiveMsg(String message){   
           System.out.println("ReceiveMessage: <" + message + ">");   
           latch.countDown();    
       }   
    }   
   
4、配置消息监听以及监听执行方法---注入消息接收者(订阅者)    
   
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
    
5、编写测试队列控制层接口         

    /**  
     * @ClassName: PublisherController  
     * @Description:  消息队列测试控制层   
     * @author: orange   
     * @date: 2018/8/7 15:06   
     */   
    @RestController   
    public class PublisherController {   
    
        @Autowired   
        private StringRedisTemplate stringRedisTemplate;   
    
        @RequestMapping(value = "pub")
        public String testPublishMsg(){
            System.out.println("开始发送消息>>>>>>>>>>");
            stringRedisTemplate.convertAndSend("msg", "TestQueueMessageSuccess");
            return "success";
        }
    }
    
6、打印结果为一下内容及为成功    
    
    开始发送消息>>>>>>>>>>
    ReceiveMessage: <TestQueueMessageSuccess> 
    
    
    
    