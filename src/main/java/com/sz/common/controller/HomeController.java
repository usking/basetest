package com.sz.common.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sz.common.constant.Consts;
import com.sz.common.service.UserService;
import com.sz.common.shiro.AuthenticationExtendException;
import com.sz.common.shiro.UsernamePasswordExtendToken;
import com.sz.common.test.TestUser;
import com.sz.common.test.UserDB;
import com.sz.common.util.CommonUtils;
import com.sz.common.util.VerifyCode;
import com.sz.common.vo.SessionInfo;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/captcha.jpg")
	public void captcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String captcha=VerifyCode.generateTextCode(VerifyCode.TYPE_NUM_ONLY,4,"0oOilJI");
		request.getSession().setAttribute(VerifyCode.SESSION_CAPTCHA,captcha);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("image/jpeg");
		BufferedImage bim=VerifyCode.generateImageCode(captcha, 98, 37, 10,true,new Color(255,255,255),Color.darkGray,null);
		ImageIO.write(bim, "JPEG",response.getOutputStream());
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		try {
			System.out.println("执行了login");
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				return "redirect:main";
			}
			boolean rememberMe=subject.isRemembered();
			if(rememberMe){//自动登录
				String username=(String)subject.getPrincipal();
				TestUser user=UserDB.getUser(username);
				UsernamePasswordExtendToken token = new UsernamePasswordExtendToken(username, user.getPassword(),null,rememberMe,request.getRemoteHost(),false);
				token.setRememberMe(rememberMe);
				subject.login(token);
				SessionInfo sessionInfo=userService.getSessionInfo(String.valueOf(user.getId()),username, request.getRemoteHost());
				this.getSession().setAttribute("sessionInfo", sessionInfo);
				String csrftoken=CommonUtils.getUUID();
				this.getSession().setAttribute(Consts.CSRF_TOKEN_NAME, csrftoken);
				return "redirect:main";
			}
		} catch(AuthenticationExtendException ex){
			ex.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return this.getJspPath("login");
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		try {
			System.out.println("执行了logout");
			HttpSession session=request.getSession();
			if(session!=null){
				session.invalidate();
			}
			Subject subject = SecurityUtils.getSubject();
	        if (subject != null) {           
	            subject.logout();
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:login";
	}
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){
		try{
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return this.getJspPath("main");
	}
	
}
