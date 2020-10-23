package com.sz.test;

import java.util.concurrent.atomic.AtomicReference;

public class MySpinLock {
	AtomicReference<Thread> atomocReference=new AtomicReference<Thread>();
	//加锁
	public void myLock() {
		Thread thread=Thread.currentThread();
		System.out.println(Thread.currentThread()+"==>myLock");
		//自旋锁
		while(!atomocReference.compareAndSet(null, thread)) {
			
		}
	}
	//解锁
	public void myUnLock() {
		Thread thread=Thread.currentThread();
		System.out.println(Thread.currentThread()+"==>myUnLock");
		atomocReference.compareAndSet(thread, null);
	}
}
