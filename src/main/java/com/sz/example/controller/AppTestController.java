package com.sz.example.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sz.common.aop.NoCheckToken;
import com.sz.common.controller.BaseController;
import com.sz.common.service.AppLoginService;
import com.sz.common.vo.Result;
import com.sz.common.vo.SessionInfo;

@Controller
@RequestMapping("/app")
public class AppTestController {
	
	@Resource
	private AppLoginService appLoginService;
	
	public SessionInfo getSessionInfo(HttpServletRequest request) {
		String deviceType=request.getParameter("deviceType");
		String userid=request.getParameter("userid");
		String token=request.getParameter("token");
		SessionInfo sessionInfo=appLoginService.getSessionInfo(deviceType, userid, token);
		return sessionInfo;
	}
	
	@RequestMapping("/test01")
	@ResponseBody
	public Result test01(HttpServletRequest request) {
		System.out.println("执行了test01");
		SessionInfo sessionInfo=this.getSessionInfo(request);
		System.out.println("sessionInfo信息======"+JSONObject.toJSONString(sessionInfo));
		return Result.success("test01");
	}
	
	@RequestMapping("/test02")
	@ResponseBody
	@NoCheckToken
	public Result test02() {
		System.out.println("执行了test02");
		return Result.success("test02");
	}
	
}
