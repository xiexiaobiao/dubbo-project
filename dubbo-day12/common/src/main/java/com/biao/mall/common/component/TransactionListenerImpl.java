package com.biao.mall.common.component;

import com.biao.mall.common.service.DubboOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname TransactionListenerImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-31 22:14
 * @Version 1.0
 **/

//@Slf4j
public class TransactionListenerImpl implements TransactionListener {

    private final static Logger log = LoggerFactory.getLogger(TransactionListenerImpl.class);

    private Message msg;
    private Object arg;
    private DubboOrderService orderService;

    public TransactionListenerImpl(){ }

    public TransactionListenerImpl(Message msg, Object arg, DubboOrderService orderService){
        this.msg = msg;
        this.arg = arg;
        this.orderService = orderService;
    }

    //事务数量累加器
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    //本地事务执行状态记录map
    private ConcurrentHashMap<String,Integer> localTransMap = new ConcurrentHashMap<>(16);

    //used to execute local transaction when send half message succeed
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        int value = transactionIndex.getAndIncrement();
        int status = 0;
        try{
            //订单付款处理
            /**特别注意，这里的this.arg不是形参Object arg*/
            orderService.payOrderTrans((String)this.arg);
            status = 1;
        }catch (Exception e){
            status = 2;
        }
        localTransMap.put(msg.getTransactionId(), status);
        switch (status) {
            case 0:
                //会多次回查，直到达到最大回查次数
                return LocalTransactionState.UNKNOW;
            case 1:
                //一次回查即结束
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                //一次回查即结束
                return LocalTransactionState.ROLLBACK_MESSAGE;
            default:{}
        }
        return LocalTransactionState.UNKNOW;
    }

    // check the local transaction status and respond to MQ check requests
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.debug("checkLocalTrans >> "+ msg.getTransactionId());
        Integer status = localTransMap.get(msg.getTransactionId());
        if (null != status) {
            switch (status) {
                case 0:
                    //会多次回查，直到达到最大回查次数
                    return LocalTransactionState.UNKNOW;
                case 1:
                    //一次回查即结束
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    //一次回查即结束
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
