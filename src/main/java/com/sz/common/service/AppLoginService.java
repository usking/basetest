package com.sz.common.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sz.common.test.TestUser;
import com.sz.common.test.UserDB;
import com.sz.common.util.CommonUtils;
import com.sz.common.util.RedisUtils;
import com.sz.common.vo.SessionInfo;

@Service
public class AppLoginService {
	
	/**登录redis前缀**/
	private static String USERLOGIN_PREFIX="appUserlogin:";
	
	@Resource
	private RedisUtils redisUtils;
	
	public SessionInfo login(String username,String password) {
		TestUser user=UserDB.validUser(username, password);
		if(user!=null) {
			int userid=user.getId();
			SessionInfo sessionInfo=new SessionInfo();
			sessionInfo.setUserid(String.valueOf(userid));
			sessionInfo.setUsername(username);
			return sessionInfo;
		}else {
			return null;
		}
	}

	/**
	 * (前后分离)登录
	 * @param username
	 * @param password
	 * @param deviceType
	 * @return
	 */
	public SessionInfo login(String username,String password,String deviceType){
		SessionInfo sessionInfo=this.login(username, password);
		if(sessionInfo!=null) {
			if(StringUtils.isBlank(deviceType)) {
				deviceType="default";
			}
			sessionInfo.setDeviceType(deviceType);
			String token=CommonUtils.getUUID();
			sessionInfo.setToken(token);
			heartbeat(sessionInfo);
			return sessionInfo;
		}else {
			return null;
		}
	}
	
	/**
	 * (前后分离)退出登录
	 * @param deviceType
	 * @param userid
	 * @param token
	 */
	public void logout(String deviceType,String userid,String token) {
		String key=this.getLoginKey(deviceType, userid, token);
		redisUtils.delete(key);
	}
	
	/**
	 * (前后分离)获取sessionInfo
	 * @param userid
	 * @param token
	 * @return
	 */
	public SessionInfo getSessionInfo(String deviceType,String userid,String token){
		String key=this.getLoginKey(deviceType, userid, token);
		String data=(String)redisUtils.read(key);
		SessionInfo sessionInfo=null;
		if(data!=null){
			sessionInfo=JSONObject.parseObject(data, SessionInfo.class);
		}
		return sessionInfo;
	}
	
	/**
	 * (前后分离)发送心跳数据 保持会话
	 */
	public void heartbeat(SessionInfo sessionInfo){
		String sessionInfoStr=JSONObject.toJSONString(sessionInfo);
		String loginMode="1";
		if("1".equals(loginMode)){
			String key=this.getLoginKey(sessionInfo.getDeviceType(), sessionInfo.getUserid(), "");
			redisUtils.write(key, sessionInfoStr, 60*300l);//300分钟
		}else{
			String key=this.getLoginKey(sessionInfo.getDeviceType(), sessionInfo.getUserid(), sessionInfo.getToken());
			redisUtils.write(key, sessionInfoStr, 60*300l);
		}
	}
	
	/**
	 * (前后分离)获取登录存储在redis里的key
	 * @param deviceType
	 * @param userid
	 * @param token
	 * @return
	 */
	public String getLoginKey(String deviceType,String userid,String token){
		if(StringUtils.isBlank(deviceType)) {
			deviceType="default";
		}
		String loginMode="1";
		String key="";
		if("1".equals(loginMode)){
			key=USERLOGIN_PREFIX+deviceType+":"+userid;
		}else{
			key=USERLOGIN_PREFIX+deviceType+":"+userid+":"+token;
		}
		return key;
	}
}
