package com.biao.rmq;


import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Producer {
    public static void main(String[] args) throws Exception{
        // RPC hook to execute per each remoting command execution.
        // 添加钩子函数
        RPCHook rpcHook = new RPCHook() {
            @Override
            public void doBeforeRequest(String remoteAddr, RemotingCommand request) {
                System.out.println("Producer RPC hook doBeforeRequest");
            }

            @Override
            public void doAfterResponse(String remoteAddr, RemotingCommand request, RemotingCommand response) {
                System.out.println("Producer RPC hook doAfterResponse");
            }
        };
        //DefaultMQProducer(final String namespace, final String producerGroup, RPCHook rpcHook,
        //        boolean enableMsgTrace, final String customizedTraceTopic)
        // Namespace for this MQ Producer instance.
        // DefaultMQProducer mqProducer = new DefaultMQProducer("namespace01","producerGroup01",
        //        rpcHook,true,"customizedTraceTopic01");

        // 以下为简单类型MQProducer
        DefaultMQProducer mqProducer = new DefaultMQProducer("producerGroup01");
        // producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
        mqProducer.setNamesrvAddr("192.168.1.224:9876");
        mqProducer.setInstanceName("rocket-Producer-instance01");
        mqProducer.start();

        for (int i = 0; i < 3; i++) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Message msg = new Message("topic01","tag01",
                    ("message test. time:"+localDateTime).getBytes(StandardCharsets.UTF_8));
            // 获取消息发送的状态信息，包含了sendStatus/msgId/offsetMsgId/messageQueue/queueOffset，
            // 从而可以进一步根据SendResult内容做业务处理
            SendResult sendResult = mqProducer.send(msg,500L);
            System.out.println(sendResult);
        }

        mqProducer.shutdown();
    }
}
