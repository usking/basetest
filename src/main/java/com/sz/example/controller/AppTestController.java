package com.sz.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.sz.common.aop.NoCheckToken;
import com.sz.common.service.AppLoginService;
import com.sz.common.vo.Result;
import com.sz.common.vo.SessionInfo;

@Controller
@RequestMapping("/app")
public class AppTestController {
	
	@Resource
	private AppLoginService appLoginService;
	@Resource
	private RestTemplate restTemplate;
	
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
	
	@RequestMapping("/test03")
	@ResponseBody
	@NoCheckToken
	public Result test03(String userid,String token) {
		try {
			System.out.println("执行了test03");
			
			//POST传参
			String url="http://127.0.0.1:8080/basetest/app/test04";
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        MultiValueMap<String, String> param= new LinkedMultiValueMap<>();
	        param.add("userid", userid);
	        param.add("token", token);
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, headers);
	        ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, param);
	        
			//GET传参
//	        String url="http://127.0.0.1:8080/basetest/app/test04?userid={userid}&token={token}";
//	        Map<String,String> param=new HashMap<>();
//	        param.put("userid", userid);
//	        param.put("token", token);
//			ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class, param);
			
			
			String s=forEntity.getBody();
			
			System.out.println(s);
			return Result.success(s);
		}catch(Exception ex) {
			ex.printStackTrace();
			return Result.error(ex.getMessage());
		}
	}
	
	@RequestMapping("/test04")
	@ResponseBody
	public Result test04(HttpServletRequest request,String userid,String token) {
		System.out.println("执行了test04"+","+userid+","+token);
		return Result.success("test04");
	}
	
}
