package com.biao.rmq;

import org.apache.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws Exception{

/*        MQAdmin mqAdmin = new ;
        MQAdminImpl mqAdmin1 = new MQAdminImpl();*/

        // RPC hook to execute per each remoting command execution.
        // 添加钩子函数

        RPCHook rpcHook = new RPCHook() {
            @Override
            public void doBeforeRequest(String remoteAddr, RemotingCommand request) {
                System.out.println("Consumer RPC hook doBeforeRequest");
            }

            @Override
            public void doAfterResponse(String remoteAddr, RemotingCommand request, RemotingCommand response) {
                System.out.println("Consumer RPC hook doAfterResponse");
            }
        };

        // Strategy Algorithm for message allocating between consumers
        AllocateMessageQueueStrategy allocateMessageQueueStrategy = new AllocateMessageQueueStrategy() {
            @Override
            public List<MessageQueue> allocate(String consumerGroup, String currentCID, List<MessageQueue> mqAll, List<String> cidAll) {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        };

        // 2022年将移除DefaultMQPullConsumer，已@Deprecated，推荐未来使用 DefaultLitePullConsumer，但还没实现，这是耍人？？
        /*DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("namespace02",
                "consumerGroup02",rpcHook,allocateMessageQueueStrategy,
                true,"customizedTraceTopic01");*/
        // simple constructor
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumerGroup02");
        consumer.setNamesrvAddr("192.168.1.224:9876");
        // Specify where to start in case the specified consumer group is a brand new one.
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // tag 的匹配模式 only support or operation such as "tag1 || tag2 || tag3"
        consumer.subscribe("topic01", "*");
        // A MessageListenerConcurrently object is used to receive asynchronously delivered messages concurrently
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                // 对消息的处理业务
                System.out.printf("%s Receive New Messages:%n", Thread.currentThread().getName());
                for (MessageExt msg : msgs
                     ) {
                    System.out.println("host >> " + msg.getBornHost());
                    System.out.println("MsgId >> " + msg.getMsgId());
                    System.out.println("body >> "  + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.setInstanceName("rocket-Consumer-instance01");
        // Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");
        //defaultMQPushConsumer.shutdown();
    }
}
