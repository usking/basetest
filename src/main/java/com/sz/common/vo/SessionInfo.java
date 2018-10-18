package com.sz.common.vo;

import java.io.Serializable;

public class SessionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String userIp;
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
