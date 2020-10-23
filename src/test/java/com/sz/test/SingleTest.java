package com.sz.test;

public class SingleTest {
	private SingleTest() {}
	private volatile static SingleTest singleTest;
	public static SingleTest getInstance() {
		if(singleTest==null) {
			synchronized (SingleTest.class) {
				if(singleTest==null) {
					singleTest=new SingleTest();
				}
			}
		}
		return singleTest;
	}
}
