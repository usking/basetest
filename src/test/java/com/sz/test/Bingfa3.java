package com.sz.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sz.common.util.CommonUtils;

/**
 * 乐观锁并发
 * 张三买A票，李四买B票，张三买A票时锁A票，不影响李四买B票（李四不用等待）
 * @author Administrator
 *
 */
public class Bingfa3 implements Runnable {
	
	public static int ticketNum1=30;
	public static int ticketNum2=20;
	
	public static List<String> keys=new ArrayList<String>();//模拟多种票，每种票自己并发，不影响另一种票

	Lock lock = new ReentrantLock();
	
	public Bingfa3() {}
	
	@Override
	public void run() {
		String key=getKey();
		//卖票
		try {
			LockUtils.lock(key);//乐观锁，只锁当前的票
			//lock.lock();//锁全部的票
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
			LockUtils.unlock(key);
			//lock.unlock();
			//System.out.println("（"+LockUtils.lockMap.size()+"）");
		}
	}

	public static void buy() {
		doKeyList();
		//买票
		Bingfa3 p=new Bingfa3();
		for(int i=1;i<=60;i++) {
			new Thread(p).start();
		}
		
	}
	
	public static void doKeyList() {
		keys=new ArrayList<String>();
		for(int i=1;i<=60;i++) {
			String key="A";
			if(i%2==0) {
				key="B";
			}
			keys.add(key);
		}
	}
	
	public static String getKey() {
		int size=keys.size();
		int random=new Random().nextInt(size);
		String key=keys.get(random);
		keys.remove(random);
		return key;
	}
	
	public static void main(String[] args) {
		try {
			
			buy();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

	

}
