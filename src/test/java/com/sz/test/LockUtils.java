package com.sz.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 乐观锁
 *
 */
public class LockUtils {
	public static Map<String, LockInfo> lockMap = new HashMap<String, LockInfo>();
	private static Lock myLock = new ReentrantLock();
	private static class LockInfo{
		Lock lock;
		int count;
	}
	
	public static void lock(String key){
		LockInfo lockInfo=null;
		try {
			myLock.lock();
			lockInfo = lockMap.get(key);
			if(lockInfo == null){
				lockInfo = new LockInfo();
				lockInfo.lock = new ReentrantLock();
				lockMap.put(key, lockInfo);
			}
			lockInfo.count++;
			
		}finally {
			myLock.unlock();
		}
			
		lockInfo.lock.lock();
	}
	
	public static void unlock(String key){
		try {
			myLock.lock();
			LockInfo lockInfo = lockMap.get(key);
			if(lockInfo != null){
				lockInfo.lock.unlock();
				lockInfo.count--;
				if(lockInfo.count <= 0){
					lockMap.remove(key);
				}
			}
		}finally {
			myLock.unlock();
		}
		
	}
}
