package com.sz.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordExtendToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;
	private String captcha;
	private boolean isCaptcha;//是否验证验证码

    public boolean isCaptcha() {
		return isCaptcha;
	}

	public void setCaptcha(boolean isCaptcha) {
		this.isCaptcha = isCaptcha;
	}

	public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public UsernamePasswordExtendToken() {
        super();
    }
    
    public UsernamePasswordExtendToken(String username, String password, String captcha,boolean rememberMe,String host,boolean isCaptcha) {
        super(username, password,rememberMe,host);
        this.captcha = captcha;
        this.isCaptcha=isCaptcha;
    }
}
