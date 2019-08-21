package com.sz.common.vo;

public class Result {
	
	private String code;
	private String message;
	private Object data;
	
	private static String SUCCESS_CODE="200";
	private static String ERROR_CODE="500";

	private Result() {
		super();
	}
	
	public static Result success(Object data) {
		Result result=response(SUCCESS_CODE,"",data);
		return result;
	}
	
	public static Result error(String message) {
		Result result=response(ERROR_CODE,message,null);
		return result;
	}
	
	public static Result error(String code,String message) {
		Result result=response(code,message,null);
		return result;
	}
	
	public static Result response(String code,String message,Object data) {
		Result result=new Result();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
