package com.sz.rabbitmq;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 下载erlang 并配置环境变量
 * 延迟消息需下载插件http://www.rabbitmq.com/community-plugins.html
 *   下载rabbitmq_delayed_message_exchange
 * 启用插件
 *   rabbitmq-plugins enable rabbitmq_management
 *   rabbitmq-plugins enable rabbitmq_delayed_message_exchange
 * 禁用插件
 *   rabbitmq-plugins disable rabbitmq_management
 * 开启服务 进入rabbitmq sbin文件夹 输入命令rabbitmq-server.bat
 * 再打开一个cmd，输入命令
 * 创建用户: rabbitmqctl.bat add_user 用户名 密码
 * 给用户添加管理员角色: rabbitmqctl.bat set_user_tags 用户名 administrator
 * 创建虚拟主机: rabbitmqctl.bat add_vhost vhost名称
 * 设置用户在虚拟主机上的权限: rabbitmqctl.bat set_permissions -p vhost名称 用户名 ".*" ".*" ".*"
 * 管理端页面地址: http://127.0.0.1:15672
 * 
 * @author sjz
 *
 */
public class MessageConsumer {
	@Resource
	private AmqpTemplate amqpTemplate;
	
	public void onMessage(Object message) {
		System.out.println("######开始消费######"+new Date());
		try{
			System.out.println(message);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("######消费结束######");
	}
	
	/**
	public void onMessage(Message message) {
		System.out.println("######开始消费######");
		try{
			byte[] msg=message.getBody();
			System.out.println(message.toString());
			System.out.println(new String(msg,"UTF-8"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("######消费结束######");
	}
	**/
	
	/**
	 * fanout
	 * 延迟消息
	 * 两种不同的消息延迟方式
	 *  1.传入delay延迟的时间
	 *  2.在xml里设置好延迟时间
	 */
	public void sendMsg() {
		System.out.println("发送消息a "+new Date());
		amqpTemplate.convertAndSend("exchange1",null,"你好啊  212312",new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(3000);
				return message;
			}
		});
//		amqpTemplate.convertAndSend("exchange2",null,"hello 哈哈");
	}
	
	/**
	 * direct
	 */
	public void sendMsg2() {
		System.out.println("发送消息b "+new Date());
		amqpTemplate.convertAndSend("exchange3","queue3Key","我的exchange3",new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(3000);
				return message;
			}
		});
	}
	
	/**
	 * topic
	 */
	public void sendMsg3() {
		System.out.println("发送消息c "+new Date());
		amqpTemplate.convertAndSend("exchange4","queue4Pattern","我来了 哈哈e4",new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(3000);
				return message;
			}
		});
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-rabbitmq.xml"});
		MessageConsumer s=context.getBean("messageConsumer",MessageConsumer.class);
		s.sendMsg();
//		s.sendMsg2();
//		s.sendMsg3();
		System.out.println("main执行结束");
	}
}
