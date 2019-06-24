package com.sz.example.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SyncTest implements Runnable {
	
	private int index;
	private AmqpTemplate amqpTemplate;
	
	SyncTest(){}
	SyncTest(int index,AmqpTemplate amqpTemplate){
		this.index=index;
		this.amqpTemplate=amqpTemplate;
	}

	@Override
	public void run() {
		addMq();
	}
	
	public void addMq() {
		Map<String,Object> map=new HashMap<>();
		map.put("index", index);
		amqpTemplate.convertAndSend("exchange5","queue5Key",map);
	}

	public static void main(String[] args) {
		try {
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
			AmqpTemplate amqpTemplate=context.getBean("amqpTemplate", AmqpTemplate.class);
			for(int i=0;i<10;i++) {
				SyncTest t=new SyncTest(i,amqpTemplate);
				new Thread(t).start();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行完成");
	}

}
