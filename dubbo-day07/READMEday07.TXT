注意sql脚本的变化！

目标：
1，模拟业务逻辑：未付款订单完成后，通过RPC进行服务调用，减库存 。
2. 雪花算法生成订单id
3. dubbo 消费方，服务方注解式 配置

项目架构：新建的模拟商城系统，多module模式，common为公共模块，提供DB的entity，service，mapper，dao；stock为库存模块；business为交易模块；
common为公共接口类，具体实现在其他module，当business引用stock(库存)模块功能时，我们约定只能引用service，不可引用dao；

收获：
1，lombok通过设置idea，恢复了，AnnotationProcessors里面，具体可上网搜索一下。

2，POJO的时间类型Date全部改为LocalDateTime类型，这个是1.8开始有的，高效线程安全，且前台比如Vue传给后台的都是ISO时间格式：
 eg: "2019-08-05T00:00:00",有带T的，但这里有个很隐蔽的坑：druid和mbg的版本要搭配好，否则出现,druid无法解析Date->LocalDateTime
 >>>>attempting to get column 'gmt_create' from result set.  Cause: java.sql.SQLFeatureNotSupportedException
  亲测：mbg3.1.2+druid1.1.18报错。mbg3.1.2+druid1.1.19正常！

2，模块间不能循环依赖，如 A-->B,B-->A,会提示出错：Circular dependency between the following tasks:
   解决方法：将B中的部分转移到A，避免循环。

4.对Db中自增长的字段，做DAO插入时，要在entity上加上@TableId(type = IdType.AUTO) （待测试）

4. 错误 >>>>>>>>>>  qos-server can not bind localhost:22222,
  知识点：Qos=Quality of Service，qos是Dubbo的在线运维命令，可以对服务进行动态的配置、控制及查询,原因：provider已经使用了默认的qos端口
   导致consumer启动时使用默认的出错，配置为不同的端口即可。
5. com.alibaba.dubbo.rpc.RpcException: Unsupported client type: curator, supported client type is grizzly mina netty netty3 netty4
  可能的问题：org.apache.curator 依赖未加入

 6. ### SQL: UPDATE dubbo_stock  SET gmt_create=?, gmt_modified=?, item_id=?, quantity=?, quantity_lock=?
  ### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '30001' for key 'item_id_UNIQUE'
  问题分析：这里提示键值重复，我在DB中将该字段设置为UQ，目的是防止重复，这里却产生了问题，因为mybatis的单表默认方法：dubboStockDao.update(stockEntity,qw)
  生成的SQL是除了PK外，直接set其他所有字段，导致无法更新。
  解决：1.dao中自己写update方法，除去UQ字段  2，更好的：DB中的字段，都不要使用UQ，应该在应用层处理，这是个原则，DB只存数据，尽量少逻辑

7.我认为是一个大收获，看到了分布式事务的典型场景。
  business，和stock模块中的所有update相关方法都加了@Transational，但是business中出错时，stock中的update并不会回退！只有business模块的
  数据回退了，因为stock模块的事务是独立发起的，与business事务无关，这里就需要使用分布式事务了，

8.一个细节：dubboStockDao.update(stockEntity,qw)，，，qw可以为null，如果qw为null，这下就完蛋啦。直接会会update全表！
  务必确保 qw要非空！！！

9.一个大坑：报错 ： invocation method error.
   无法使用RPC调用 update(stockEntity,qw)，mybatis-plus 官方已经明确有说wrapper不支持RPC通信？？？
  解决小技巧：RPC调用update(stockEntity)，服务提供者本地使用update(stockEntity,qw)，或者updateById(stockEntity)其中的qw(QueryWrapper)使用
  itemId来构造即可，这样就不用自己去写dao的update方法了！

3, postman测试验证，大功告成！ post提交,内容 选 raw + json，
  {
  	"gmtCreate": "2019-08-15T00:00:00",
  	"userId": "da",
  	"orderDesc": "好货.",
  	"itemList":[{
  			"itemId": "30001",
  			"name": "手表",
  			"quantity": 2
  		},
  		{
  			"itemId": "60003",
  			"name": "电池",
  			"quantity": 3
  		},
  		{
  			"itemId": "50002",
  			"name": "笔记本",
  			"quantity": 1
  		}
  	]
  }



