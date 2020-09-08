package com.sz.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sz.common.util.CommonUtils;

public class IdTest implements Runnable {
	
	static List<Long> list=new ArrayList<>();
	
	static Sequence se=new Sequence(0, 0);
	
	
	
	public IdTest() {
	}
	
	public IdTest(Sequence se) {
		this.se=se;
	}
	
	

	@Override
	public void run() {
		
//		long time=System.currentTimeMillis();
		
		long str=CommonUtils.getRandomID();
	
//		String str=String.valueOf(se.nextId());
//		System.out.println(str);
		if(list.contains(str)) {
			System.out.println(str);
		}
		list.add(str);
		
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
//			Sequence se=new Sequence(0, 0);
			ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
			for(int i=0;i<100000;i++) {
				IdTest idTest=new IdTest();
//				new Thread(idTest).start();
				cachedThreadPool.execute(idTest);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

}
