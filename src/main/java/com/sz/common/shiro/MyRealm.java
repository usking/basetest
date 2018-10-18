package com.sz.common.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.sz.common.test.TestUser;
import com.sz.common.test.UserDB;
import com.sz.common.util.VerifyCode;


public class MyRealm extends AuthorizingRealm {

	
	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("执行了登录认证");
		
		UsernamePasswordExtendToken token = (UsernamePasswordExtendToken) authcToken;
		String username=token.getUsername();
		String password=token.getPassword()==null?"":new String((char[])token.getPassword());
		String captcha=token.getCaptcha();
		
		boolean isCapcha=token.isCaptcha();
		if(isCapcha){
			//验证码是否正确
			String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(VerifyCode.SESSION_CAPTCHA);
			if(captcha==null || !captcha.equalsIgnoreCase(sessionCaptcha)){
				throw new AuthenticationExtendException("captcha_error");
			}
		}
		
		//验证用户名和密码
		TestUser user=UserDB.validUser(username, password);
		if(user!=null){
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(username, password, this.getName());
			return authcInfo;
		}else{
			throw new AuthenticationExtendException("username_password_error");
		}
	}
	
	
	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行了权限认证");
		String currentUsername = (String)super.getAvailablePrincipal(principals);
		
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		List<String> roleList=new ArrayList<String>();
		List<String> permissionList=new ArrayList<String>();
		
		TestUser user=UserDB.getUser(currentUsername);//查询权限
		if(user.getPermissions()!=null){
			permissionList.addAll(user.getPermissions());
		}

		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
	}

}
