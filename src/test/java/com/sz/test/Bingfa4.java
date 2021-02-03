package com.sz.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.sz.common.util.CommonUtils;

/**
 * 乐观锁并发，redis分布式锁
 * 张三买A票，李四买B票，张三买A票时锁A票，不影响李四买B票（李四不用等待）
 * @author Administrator
 *
 */
@Component
public class Bingfa4 implements Runnable {
	
	@Resource
	RedisLockUtils redisLockUtils;
	
	public static int ticketNum1=30;
	public static int ticketNum2=20;
	
	public static List<String> keys=new ArrayList<String>();//模拟多种票，每种票自己并发，不影响另一种票
	
	public Bingfa4() {}
	
	@Override
	public void run() {
		String key=getKey();
		//卖票
		try {
			redisLockUtils.lock(key, key, 1*60);//秒

			Thread.sleep(1000);
			if("A".equals(key)) {
				if(ticketNum1>0) {
					ticketNum1--;
				}
				System.out.println(key+"===="+ticketNum1+"（"+CommonUtils.dateFormat(new Date(), null)+"）");
			}else if("B".equals(key)) {
				if(ticketNum2>0) {
					ticketNum2--;
				}
				System.out.println(key+"===="+ticketNum2+"（"+CommonUtils.dateFormat(new Date(), null)+"）");
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			redisLockUtils.unlock(key);
		}
	}

	public void buy(Bingfa4 p) {
		doKeyList();
		//买票
		for(int i=1;i<=60;i++) {
			new Thread(p).start();
		}
		
	}
	
	public void doKeyList() {
		keys=new ArrayList<String>();
		for(int i=1;i<=60;i++) {
			String key="A";
			if(i%2==0) {
				key="B";
			}
			keys.add(key);
		}
	}
	
	public String getKey() {
		int size=keys.size();
		int random=new Random().nextInt(size);
		String key=keys.get(random);
		keys.remove(random);
		return key;
	}
	
	public static void main(String[] args) {
		try {
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-redis.xml"});
			Bingfa4 s=context.getBean("bingfa4",Bingfa4.class);
			s.buy(s);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

	

}
