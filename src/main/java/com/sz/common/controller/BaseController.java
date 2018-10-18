package com.sz.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sz.common.vo.SessionInfo;

@Controller
@Component
@RequestMapping("/common")
public class BaseController {
	/** /WEB-INF/views/ **/
	public final String JSP_PATH="/WEB-INF/views/";
	
	public final String ENCODING="UTF-8";
	
	protected int getPageSize(HttpServletRequest request,int defaultPageSize){
		String pageSize=request.getParameter("pageSize");//每页显示条数
		if(pageSize==null || "".equals(pageSize) || ("undefined").equals(pageSize)){
			pageSize=String.valueOf(defaultPageSize);
		}
		return Integer.parseInt(pageSize);
	}
	
	protected int getPageNo(HttpServletRequest request,int defaultPageNo){
		String pageNo=request.getParameter("pageNo");//当前页号
		if(pageNo==null || "".equals(pageNo) || ("undefined").equals(pageNo)){
			pageNo=String.valueOf(defaultPageNo);
		}
		return Integer.parseInt(pageNo);
	}
	
	protected String getJspPath(String path){
		return JSP_PATH+path;
	}
	
	protected Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	
	protected SessionInfo getSessionInfo(){
		SessionInfo sessionInfo=(SessionInfo)getSession().getAttribute("sessionInfo");
		return sessionInfo;
	}
	
}
