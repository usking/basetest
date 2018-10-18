package com.sz.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sz.common.util.CommonUtils;

public class CommonHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg) throws Exception {
//		if(arg instanceof HandlerMethod){
//			HandlerMethod handler=(HandlerMethod)arg;
//			NoCheckToken noCheckToken=handler.getMethod().getAnnotation(NoCheckToken.class);
//			if(noCheckToken!=null){
//				return true;
//			}
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private boolean doBrowser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uri=request.getRequestURI();
		if(uri.indexOf("/error")>-1){
			return true;
		}
		String agent=request.getHeader("User-Agent");
		 String browser=CommonUtils.getBrowserName(agent);
		 if("ie8".equals(browser)){
			 response.sendRedirect("error/browser");
			 return false;
		 }
		return true;
	}

}
