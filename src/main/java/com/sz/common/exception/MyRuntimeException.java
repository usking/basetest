package com.sz.common.exception;
/**
 * @author sjz
 */
public class MyRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode = "";

	public MyRuntimeException(String msg){
		super(msg);
	}
	
	public MyRuntimeException(Throwable cause){
		super(cause);
	}
	
	public MyRuntimeException(String msg, Throwable cause){
		super(msg,cause);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String code){
		errorCode = code;
	}
}
