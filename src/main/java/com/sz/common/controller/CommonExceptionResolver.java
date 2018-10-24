package com.sz.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CommonExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg,Exception ex) {
		System.out.println("######执行了CommonExceptionResolver######");
		PrintWriter out=null;
		if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {// 是ajax请求
			try {
				out=response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.print(ex.getMessage());
			out.flush();
			out.close();
		}else {
			return new ModelAndView("/WEB-INF/views/error/500");
		}
		return null;
	}

}
