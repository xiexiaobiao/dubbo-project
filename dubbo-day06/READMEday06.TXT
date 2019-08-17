
目标：
(day05)
1.使用rabbitMQ实现服务间通信  business模块发送消息，stock模块接收并处理消息
2，统一异常处理 a，所有服务端响应封装为ResEntity{code,msg,data}  b,@ControllerAdvice加@ExceptionHandler统一
处理controller抛出的异常
3.使用@WebFilter进行CorsFilter注入，解决跨域
(day06）
4.dubbo方式实现 服务的注册与调用 ,business模块模拟订单保存，RPC调用stock模块的库存处理。


项目架构：新建的模拟商城系统，多module模式，common为公共模块，提供DB的entity，service，mapper，dao；stock为库存模块；business为交易模块；
common为公共接口类，具体实现在其他module，当business引用stock模块功能时，我们约定只能引用service，不可引用dao；

收获：
1.关于dubbo的@Service注解无效的问题：a，两个不同的@Service肯定是可以同时存在的  b，dubbo的@service不能和@Transactional 同时存在
  c，花了一晚实测：：扫描不到impl类的问题解决：在启动类上加@DubboComponentScan("com.biao.mall.stock")比
     @EnableDubbo(scanBasePackages = "com.biao.mall.stock.impl") 有效

2.idea2019版本有个bug，和lombok兼容性很差，导致lombok注解无效，getter/setter要手动生成一下

3.编译出现 >>>>>>>>>> unmappable character for encoding GBK,
  对汉字无法编译的，在build.gradle中设置
   tasks.withType(JavaCompile) {
       options.encoding = "UTF-8"
   }

4.postman测试：POST -->  body：
   http://localhost:8083/order/one
   //这里要特别注意 ：a.日期格式 b.boolean类型与字段对应关系,不要前缀is："expired" --> DubboOrderEntity.isExpired
   //阿里编码规约说POJO类不要使用isExpired是有道理的！！！
{
	"id": 20014,
	"gmtCreate": "2019-08-05 00:00:00",
	"gmtModified": "2019-08-05 00:00:00",
	"orderId": "01-20190805-001",
	"detailId": "3004",
	"paid": true,
	"expired": false,
	"orderDesc": "my test order."
}

5. jackson不能解析上面时间字符串对象的问题解决：https://blog.csdn.net/qq906627950/article/details/79503801
nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize
value of type `java.util.Date` from String "2019-08-05 00:00:00:000":
方案：添加能解析的日期格式：com.biao.mall.common.util.MyDateFormat，并注入：com.biao.mall.common.conf.WebConfig

