package com.sz.rocketmq;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

/******
修改内存占用
/bin/runserver.cmd
/bin/runbroker.cmd

配置环境变量
ROCKETMQ_HOME
D:\rocketmq-all-4.7.1-bin-release

启动NameServer
mqnamesrv.cmd
成功后显示：The Name Server boot success. serializeType=JSON

启动Broker
mqbroker.cmd -n 公网IP:9876 autoCreateTopicEnable=true
错误: 找不到或无法加载主类 Files\Java\jdk1.8.0_172\lib;C:\Program 原因是Program Files中间有空格。
解决办法：runbroker.cmd第26行set CLASSPATH=.;%BASE_DIR%conf;%CLASSPATH% 加引号 set CLASSPATH=.;%BASE_DIR%conf;"%CLASSPATH%"
成功后显示：The broker[DESKTOP-U6DRGDU, 1.1.1.57:10911] boot success. serializeType=JSON and name server is localhost:9876
******/

@Component
public class RocketmqConsumerService {

	private DefaultMQPushConsumer consumer = null;

	@PostConstruct
	public void initMQConsumer() {
        consumer = new DefaultMQPushConsumer("defaultGroup");
        consumer.setNamesrvAddr("1.1.1.107:9876");
        try {
            consumer.subscribe("TopicTest", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
 
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.println("接收消息： " + new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

}
