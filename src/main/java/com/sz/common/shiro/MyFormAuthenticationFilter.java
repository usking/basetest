package com.sz.common.shiro;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import com.sz.common.constant.Consts;
import com.sz.common.service.UserService;
import com.sz.common.test.TestUser;
import com.sz.common.test.UserDB;
import com.sz.common.util.CommonUtils;
import com.sz.common.vo.SessionInfo;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    protected final Logger logger = Logger.getLogger(getClass());
    
    @Resource
    private UserService userService;

	private String captchaParam = "captcha";
    
    private String rememberMeParam="rememberMe";

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, captchaParam);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
    	System.out.println("执行了createToken");
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean isRememberMe=isRememberMe(request);
        String host=this.getHost(request);
        boolean isCaptcha=true;
        return new UsernamePasswordExtendToken(username, password,captcha,isRememberMe,host,isCaptcha);
    }
    
    @Override
    protected boolean isRememberMe(ServletRequest request) {
        return WebUtils.isTrue(request, rememberMeParam);
    }

    /**
     * 登录成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,Subject subject,ServletRequest req,ServletResponse rsp) throws Exception {
    	System.out.println("登录成功执行onLoginSuccess");
    	HttpServletRequest request=(HttpServletRequest)req;
    	HttpServletResponse response=(HttpServletResponse)rsp;
    	String username=(String)subject.getPrincipal();
    	String ip=request.getRemoteAddr();
    	TestUser user=UserDB.getUser(username);
    	SessionInfo sessionInfo=userService.getSessionInfo(String.valueOf(user.getId()),username, ip);
    	Session session=SecurityUtils.getSubject().getSession();
    	session.setAttribute("sessionInfo", sessionInfo);
    	
    	//新增csrf session，请求时需验证
    	String csrftoken=CommonUtils.getUUID();
    	session.setAttribute(Consts.CSRF_TOKEN_NAME, csrftoken);
//    	Cookie cookie = new Cookie(Consts.CSRF_TOKEN_NAME,csrftoken);
//    	response.addCookie(cookie);
    	
    	
//    	SavedRequest savedRequest=WebUtils.getSavedRequest(request);
//    	if(savedRequest!=null && savedRequest.getRequestUrl()!=null && !savedRequest.getRequestUrl().isEmpty()){
//        	((HttpServletResponse)response).sendRedirect(savedRequest.getRequestUrl());
//    	}else{
//    		request.getRequestDispatcher("/main.do").forward(request, response);
//    	}
    	((HttpServletResponse)response).sendRedirect("main");
    	//issueSuccessRedirect(request, response);
        return false;
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,AuthenticationException e,ServletRequest request,ServletResponse response) {
    	System.out.println("登录失败执行onLoginFailure");
    	String errorMessage="";
    	String captchaErrorMessage="";
    	String errorCode=e.getMessage();
    	if("captcha_error".equals(errorCode)){
    		captchaErrorMessage="验证码错误";
    	}else if("username_password_error".equals(errorCode)){
    		errorMessage="用户名或密码错误";
    	}else{
    		errorMessage="服务器异常#"+errorCode;
    	}
    	request.setAttribute("userErrorMessage", errorMessage);
    	request.setAttribute("captchaErrorMessage", captchaErrorMessage);
    	setFailureAttribute(request, e);
    	return true;
    }
}
