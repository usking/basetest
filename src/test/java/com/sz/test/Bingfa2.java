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
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class Bingfa2 implements Runnable {
	
	public static int total=10;
	public static int success=0;
	public static int failed=0;
	
	public CountDownLatch countDownLatch;
	
	public InterProcessMutex lock;
	
	public Bingfa2() {
		
	}
	
	public Bingfa2(CountDownLatch countDownLatch,InterProcessMutex lock) {
		this.countDownLatch=countDownLatch;
		this.lock=lock;
	}
	

	@Override
	public void run() {
		try {
			//lock.acquire();
			lock.acquire(2000, TimeUnit.MILLISECONDS);
			//Thread.sleep(500);
//			long a=countDownLatch.getCount();
//			if(a==2) {
//				throw new Exception("自定义异常");
//			}
			System.out.println(total);
			if(total<=0) {
				++failed;
			}else {
				++success;
				--total;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		finally {
			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

		countDownLatch.countDown();
	}
	
	public static CuratorFramework getClient() throws Exception {
		String connectString="127.0.0.1:2181";	
		// 重试策略，初始化每次重试之间需要等待的时间，基准等待时间为1秒。
	    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder().
		        connectString(connectString).
		        connectionTimeoutMs(15 * 1000).
		        sessionTimeoutMs(60 * 100).
		        retryPolicy(retryPolicy).
		        build();
		client.start();
		return client;
		//client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(lockNode,"0".getBytes());
	}
	
	public static InterProcessMutex getLock() throws Exception {
		String lockNode="/MYLOCK";
		CuratorFramework client=getClient();
		InterProcessMutex lock = new InterProcessMutex(client, lockNode);
		return lock;
	}
	
	public static void test() {
		ThreadPoolExecutor executor=null;
		try {
			int corePoolSize=20;//核心线程数，线程并发数
			int maximumPoolSize=20;//最大线程数
			long keepAliveTime=10;//空闲线程空闲存活时间
			TimeUnit unit=TimeUnit.SECONDS;//设置 keepAliveTime的单位
			BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<>(200);//有界队列，线程数大于maximumPoolSize，则执行拒绝策略
			executor=new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
					Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
			

			InterProcessMutex lock=getLock();
			
			int size=15;
			CountDownLatch countDownLatch=new CountDownLatch(size);
			for(int i=0;i<size;i++) {
				Bingfa2 bingfa=new Bingfa2(countDownLatch,lock);
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
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

	

}
