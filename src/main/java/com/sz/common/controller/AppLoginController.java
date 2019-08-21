package com.sz.common.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz.common.aop.NoCheckToken;
import com.sz.common.service.AppLoginService;
import com.sz.common.vo.Result;
import com.sz.common.vo.SessionInfo;

@Controller
@RequestMapping("/app")
public class AppLoginController extends BaseController {
	
	@Resource
	private AppLoginService appLoginService;
	
	@RequestMapping("/login")
	@ResponseBody
	@NoCheckToken
	public Result login(String username,String password,String deviceType) {
		SessionInfo sessionInfo=appLoginService.login(username, password, deviceType);
		if(sessionInfo==null) {
			return Result.error("用户名或密码错误");
		}
		return Result.success(sessionInfo);
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Result logout(String deviceType,String userid,String token) {
		appLoginService.logout(deviceType, userid, token);
		return Result.success(null);
	}
}
