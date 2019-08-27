
（day10）目标：
1  redis生成 订单详细表的 id,有两种思路，一是使用increment(),一种是先生成一定量的ID，再取
2. 多线程MQ处理
3. MQ改为使用私有协议进行内部通信
5. 添加logback日志功能

项目架构：模拟商城系统，多module模式，common为公共模块，提供DB的entity，service，mapper，dao；stock为库存模块；business为交易模块；
common为公共接口类，具体实现在其他module，当business引用stock(库存)模块功能时，约定只能引用service，不可引用dao；logistics物流快递模块

收获：
1.

3.我认为是一个大收获，实际体会到了什么是分布式事务。(待续)
  business，和stock模块中的所有update相关方法都加了@Transational，但是business中出错时，stock中的update并不会回退！只有business模块的
  数据回退了，因为stock模块的事务是独立发起的，与business事务无关，这里就需要使用 分布式事务了，这样侧面说明事物不跨module！
  比如：前端传入订单数超过了库存，订单会成功，但库存会

4.经测试：每个exchange到queue绑定的routingKey都可以是不一样的。即可以单独绑定，互不影响。

5.redis生成 订单详细表的 id,有两种思路，一是使用increment("key",step),这种直接每次使用就生成一个递增的ID，优点是随用随取，
不必存储，但容易被猜出一天内的数量；一种是先生成一定量的ID，乱序后存储，然后使用时根据一个“+1计数器”再去取，

6.弄了几小时!service无法zk注册服务,只能订阅服务的问题，是否启动类上未添加 @EnableDubbo








