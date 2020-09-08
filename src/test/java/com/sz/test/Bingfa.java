package com.sz.test;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Bingfa implements Runnable {
	
	public static int total=10;
	public static int success=0;
	public static int failed=0;
	
	public CountDownLatch countDownLatch;
	
	public Bingfa() {
		
	}
	
	public Bingfa(CountDownLatch countDownLatch) {
		this.countDownLatch=countDownLatch;
	}

	@Override
	public void run() {
		
		synchronized (this) {
			runTest();
		}
		countDownLatch.countDown();
	}
	
	public void runTest() {
		try {
			Thread.sleep(500);
		}catch(InterruptedException ex) {}
		
		System.out.println(total);
		if(total<=0) {
			++failed;
		}else {
			++success;
			--total;
		}
		
//		try {
//			Thread.sleep(500);
//		}catch(InterruptedException ex) {}
	}
	
	
	public static void test() {
		ThreadPoolExecutor executor=null;
		try {
			int corePoolSize=20;//核心线程数，线程并发数
			int maximumPoolSize=20;//最大线程数
			long keepAliveTime=10;//空闲线程空闲存活时间
			TimeUnit unit=TimeUnit.SECONDS;//设置 keepAliveTime的单位
			BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<>(200);//有界队列，线程数大于maximumPoolSize，则执行拒绝策略
			//BlockingQueue<Runnable> workQueue=new LinkedBlockingDeque<>();//无界队列，maximumPoolSize参数无效，直至资源耗尽
			executor=new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
					Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
			
//			executor=new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//					Executors.defaultThreadFactory(),new RejectedExecutionHandler() {
//						@Override
//						public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//							System.out.println(r.toString()+"执行了自定义拒绝策略");
//						}
//					}
//			);
			
			
			int size=15;
			CountDownLatch countDownLatch=new CountDownLatch(size);
			Bingfa bingfa=new Bingfa(countDownLatch);
			for(int i=0;i<size;i++) {
				//new Thread(bingfa).start();
				executor.execute(bingfa);
			}
			countDownLatch.await();
			
			System.out.println("success:"+success);
			System.out.println("failed:"+failed);
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(executor!=null) {
				executor.shutdown();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			test();
			//int cpuNum = Runtime.getRuntime().availableProcessors();
			//System.out.println(cpuNum);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

	

}
