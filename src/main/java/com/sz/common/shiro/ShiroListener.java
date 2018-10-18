package com.sz.common.shiro;

import java.util.Date;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.sz.common.util.CommonUtils;

public class ShiroListener implements SessionListener {

	@Override
	public void onExpiration(Session session) {
		System.out.println("执行了onExpiration "+CommonUtils.dateFormat(new Date(), null));
	}

	@Override
	public void onStart(Session session) {
		System.out.println("执行了onStart "+CommonUtils.dateFormat(new Date(), null));
	}

	@Override
	public void onStop(Session session) {
		System.out.println("执行了onStop "+CommonUtils.dateFormat(new Date(), null));
	}


}
