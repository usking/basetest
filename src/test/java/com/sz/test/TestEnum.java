package com.sz.test;

public enum TestEnum {
	
	RED("红色",1),GREEN("绿色",2),YELLOW("黄色",3);
	
	private String name;
	private int index;
	private TestEnum(String name,int index){
		this.name=name;
		this.index=index;
	}
	
	public static void main(String[] args) {
		String str=TestEnum.RED.name();
		String name=TestEnum.RED.name;
		int index=TestEnum.RED.index;
		System.out.println(str);
		System.out.println(name);
		System.out.println(index);
	}
}
