package com.sz.common.test;

import java.util.ArrayList;
import java.util.List;

public class UserDB {

	public static List<TestUser> getAllUser(){
		List<TestUser> userList=new ArrayList<TestUser>();
		
		TestUser user=new TestUser();
		user.setId(1);
		user.setUsername("admin");
		user.setPassword("123456");
		List<String> permissions=new ArrayList<String>();
		permissions.add("admin:admin");
		user.setPermissions(permissions);
		userList.add(user);
		
		user=new TestUser();
		user.setId(2);
		user.setUsername("user");
		user.setPassword("user");
		userList.add(user);
		
		return userList;
	}
	
	public static TestUser validUser(String username,String password){
		List<TestUser> userList=getAllUser();
		for(TestUser user : userList){
			if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
				return user;
			}
		}
		return null;
	}
	
	public static TestUser getUser(String username){
		List<TestUser> userList=getAllUser();
		for(TestUser user : userList){
			if(username.equals(user.getUsername())){
				return user;
			}
		}
		return null;
	}
}
