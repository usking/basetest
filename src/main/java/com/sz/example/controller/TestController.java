package com.sz.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz.common.aop.TestAnnotation;
import com.sz.common.controller.BaseController;
import com.sz.common.vo.Result;
import com.sz.common.vo.ResultVo;
import com.sz.example.service.TestService;
import com.sz.rocketmq.RocketmqProducerService;

@Controller
@RequestMapping("/test")
@TestAnnotation(value="abc123456")
public class TestController extends BaseController {
	@Resource
	private TestService testService;
	@Resource
	private RocketmqProducerService rocketmqProducerService;
	
	@RequestMapping("/index")
	public String testIndex() {
//		testService.testIndex();
		return this.getJspPath("test/test");
	}
	
	@RequestMapping("/testAjax")
	@ResponseBody
	public ResultVo testAjax() {
//		testService.testIndex();
		ResultVo resultVo=new ResultVo();
		resultVo.setCode("200");
		resultVo.setMessage("运行成功");
		resultVo.setData(null);
		return resultVo;
	}
	
	@RequestMapping("/nocheck/t")
	@ResponseBody
	public void test() {
		try {
			testService.testHibernate04();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping("/test2")
	@ResponseBody
	@TestAnnotation(value="666",name="777")
	public String test2() {
		System.out.println("执行了test2");
		return "success";
	}
	
	@RequestMapping("/nocheck/testJson")
	@ResponseBody
	public String testJson() {
		try {
			testService.getJson();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return "end";
	}
	
	@RequestMapping("/testRocketmq")
	@ResponseBody
	public Result testRocketmq() {
		try {
			rocketmqProducerService.send();
			return Result.success(null);
		}catch(Exception ex) {
			ex.printStackTrace();
			return Result.error(ex.getMessage());
		}
	}
}
