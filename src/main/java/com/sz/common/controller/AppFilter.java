package com.sz.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sz.common.aop.NoCheckToken;
import com.sz.common.service.AppLoginService;
import com.sz.common.vo.Result;
import com.sz.common.vo.SessionInfo;

public class AppFilter implements HandlerInterceptor {
	
	@Resource
	private AppLoginService appLoginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg) throws Exception {
		System.out.println("===================验证token====================");
		if(arg instanceof HandlerMethod){
			HandlerMethod handler=(HandlerMethod)arg;
			NoCheckToken noCheckToken=handler.getMethod().getAnnotation(NoCheckToken.class);
			if(noCheckToken!=null){
				return true;
			}
		}
		
		String deviceType=(String)request.getParameter("deviceType");
		String userid=(String)request.getParameter("userid");
		String token=(String)request.getParameter("token");
		SessionInfo sessionInfo=appLoginService.getSessionInfo(deviceType, userid, token);
		if(sessionInfo==null || !token.equals(sessionInfo.getToken())) {
			Result result=Result.error("token_error","请登录");
			String resultJson=JSONObject.toJSONString(result);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE); 
            response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resultJson);
			return false;
		}else {
			appLoginService.heartbeat(sessionInfo);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		
	}

}
