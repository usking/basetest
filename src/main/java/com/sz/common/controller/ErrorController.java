package com.sz.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
	
	@RequestMapping("/400")
	public String error400(){
		return this.getJspPath("error/400");
	}
	
	@RequestMapping("/403")
	public String error403(){
		return this.getJspPath("error/403");
	}
	
	@RequestMapping("/404")
	public String error404(){
		return this.getJspPath("error/404");
	}
	
	@RequestMapping("/500")
	public String error500(){
		return this.getJspPath("error/500");
	}
	
	@RequestMapping("/browser")
	public String errorBrowser(){
		return this.getJspPath("error/browser");
	}
	
}
