package com.sz.common.util;

public class ValidateUtils {

	/**
	 * 验证邮箱
	 * @param mail
	 * @return
	 */
	public static boolean checkMail(String mail){
		String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		return mail.matches(regex);
	}
	
	/**
	 * 验证手机号
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile){
		String regex="^(1[3-9][0-9])[0-9]{8}$";
		return mobile.matches(regex);
	}
	
	/**
	 * 验证密码 (数字、字母、标点符号)
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password){
		String regex="^[A-Za-z0-9,.:;!?]*$";
		return password.matches(regex); 
	}
	
	/**
	 * 验证身份证号
	 * @param idcard
	 * @return
	 */
	public static boolean checkIdcard(String idcard){
		String regex="[1-9]\\d{16}[a-zA-Z0-9]{1}";
		return idcard.matches(regex);
	}
	
	/**
	 * 验证数字
	 * @param number
	 * @param digit 小数位数
	 * @param isNegative 是否允许为负数 true允许
	 * @return
	 */
	public static boolean checkNumber(String number,int digit,boolean isNegative){
		String string = digit==0?"0":("1,"+digit);
		String string2 = isNegative? "-?":"";
		String regex="^" + string2 + "\\d*(\\d\\.\\d{"+string+"})?$";
		return number.matches(regex);
	}
	
	public static void main(String[] args){
		try{
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
