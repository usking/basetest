package com.sz.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sz.common.vo.Result;

@Component
public class CommonExceptionResolver implements HandlerExceptionResolver {
	
	Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg,Exception ex) {
		System.out.println("######执行了CommonExceptionResolver######");
		logger.error(ex.getMessage(),ex);
		PrintWriter out=null;
		if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {// 是ajax请求
			response.setContentType(MediaType.APPLICATION_JSON_VALUE); 
            response.setCharacterEncoding("UTF-8");
			try {
				out=response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Result result=Result.error(ex.getMessage());
			String resultJson=JSONObject.toJSONString(result);
			out.write(resultJson);
			out.flush();
			out.close();
		}else {
			if(ex instanceof UnauthorizedException) {
				return new ModelAndView("/WEB-INF/views/error/403");
			}else {
				return new ModelAndView("/WEB-INF/views/error/500");
			}
			
		}
		return null;
	}

}
