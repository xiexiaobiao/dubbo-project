
目标：1.使用rabbitMQ实现服务间通信  business模块发送消息，stock模块接收并处理消息
2，统一异常处理 a，所有响应进行封装，为ResEntity{code,msg,data} b,@ControllerAdvice加@ExceptionHandler统一
处理controller抛出的异常
3.使用@WebFilter进行CorsFilter注入，解决跨域

项目架构：新建的模拟商城系统，多module模式，common为公共模块，提供DB的entity，service，mapper，dao；stock为库存模块；business为交易模块；
common为公共接口类，具体实现在其他module，当business引用stock模块功能时，规定只能使用service，不可引用dao；

1.刚新建的空gradle项目，就无法build，提示build failed，然后建module找不到父项目，真是××××
 解决：右边的gradle--setting--> gradle设置选择本地的gradle和jdk目录，刷新即可

2.有些模块启动后一直提示无法注入其他模块的bean，解决方法：现将该模块的启动类所在包提升，如从com.biao.mall.business ---> com.biao.mall,
提升到所有module的公共包最高级别，并且所有模块的启动类都放在公共包最高级别，才能都正常扫描所有包，
其他所谓的 scanBasePackages指定或扩大扫描包范围的方法都不行，亲测几个晚上的结果。

3.MQ中：
   创建一个vhost，系统会自动建立一些该host下的exchange；
   default Exchange不能进行Binding,也不需要进行绑定;其他任何Exchange都需要和Queue或exchange进行Binding,
   Queue属性：x-message-ttl：一个消息在队列中的存活时间
   Queue属性：x-dead-letter-exchange：当消息被拒绝或者消息过期，消息重新发送到的交换机（Exchange）的可选名称。
   Queue属性：x-dead-letter-routing-key：当消息被拒绝或者消息过期，消息重新发送到的交换机绑定的Route key的名称
   延迟消费模型：(dlx/ttl设置的)Queue --(未正常消费后)-->  exchange --(binding)-> DLQueue ---->消费者

4，多模块调用：
  Service A不应直接调用B的Dao层，而是应该调用B的Service层业务实现，因为B的Service是对其Dao的CRUD封装，在多库且有缓存的情况下，可能存在跨库操作，
  直接调用B的Dao显然是不合理的。简单的说就是为了代码结构更清晰，长远点说就是方便以后不同模块之间进行项目拆分。如果被调用service没有需要的逻辑，可再抽象
  出一个新的service供调用。

5，项目验证：
   Postman：http://localhost:8083/producer/msg
   可以看到QUEUE_A中有消息收到，3分钟后成为死信后，会进入dlq.queue_B中，

   post模拟发送对象：
   Postman：http://localhost:8083/producer/msg2
   headers：Content-Type : application/json
   发送body：{"key":"xiao2","value":"biao2"}
   控制台也可以看到整个消息流通顺序。