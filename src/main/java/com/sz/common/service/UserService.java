package com.sz.common.service;

import org.springframework.stereotype.Service;

import com.sz.common.vo.SessionInfo;

@Service
public class UserService {

	public SessionInfo getSessionInfo(String userid,String username,String ip){
		SessionInfo sessionInfo=new SessionInfo();
    	sessionInfo.setUserIp(ip);
    	sessionInfo.setUsername(username);
    	sessionInfo.setUserid(userid);
    	return sessionInfo;
	}
}
