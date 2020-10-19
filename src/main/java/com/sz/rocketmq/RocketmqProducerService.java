package com.sz.rocketmq;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RocketmqProducerService {

	
	private DefaultMQProducer producer = null;
	
	@PostConstruct
	public void initMQProducer() {
		producer = new DefaultMQProducer("defaultGroup");
        producer.setNamesrvAddr("1.1.1.107:9876");
        producer.setRetryTimesWhenSendFailed(3);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
	}
	
	public boolean send() {
		String topic="TopicTest";
		String tags="TAG-1";
		String content="测试rocketmq，hello世界";
		
        Message msg = new Message(topic, tags, "", content.getBytes());
        try {
        	System.out.println("发送了消息");
            producer.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public static void main(String[] args) {
		try {
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
			RocketmqProducerService s=(RocketmqProducerService)context.getBean("rocketmqProducerService");
			s.send();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行完成");
	}

}
