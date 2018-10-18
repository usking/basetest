package com.sz.common.shiro;

import org.apache.shiro.authc.AuthenticationException;

public class AuthenticationExtendException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public AuthenticationExtendException() {
        super();
    }
	
	public AuthenticationExtendException(String message) {
        super(message);
    }

    public AuthenticationExtendException(Throwable cause) {
        super(cause);
    }

    public AuthenticationExtendException(String message, Throwable cause) {
        super(message, cause);
    }
}
