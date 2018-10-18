package com.sz.common.exception;
/**
 * @author sjz
 */
public class MyException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode = "";

	public MyException(String msg){
		super(msg);
	}
	
	public MyException(Throwable cause){
		super(cause);
	}
	
	public MyException(String msg, Throwable cause){
		super(msg,cause);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String code){
		errorCode = code;
	}
}
