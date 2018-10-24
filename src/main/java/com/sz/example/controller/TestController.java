package com.sz.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz.common.controller.BaseController;
import com.sz.common.vo.ResultVo;
import com.sz.example.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
	@Resource
	private TestService testService;
	
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
}
