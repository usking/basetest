package com.sz.common.util;

import java.math.BigDecimal;

/**
 * @author sjz
 */
public class Mas {
	
	public static BigDecimal jia(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
	}
	
	public static BigDecimal jia(String v1,String v2){
		BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2);
	}
	
	public static BigDecimal jia(BigDecimal v1,BigDecimal v2){
        return v1.add(v2);
	}
	
	public static BigDecimal jian(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
	}
	
	public static BigDecimal jian(String v1,String v2){
		BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
	}
	
	public static BigDecimal jian(BigDecimal v1,BigDecimal v2){
        return v1.subtract(v2);
	}
	
	public static BigDecimal cheng(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
	}
	
	public static BigDecimal cheng(String v1,String v2){
		BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2);
	}
	
	public static BigDecimal cheng(BigDecimal v1,BigDecimal v2){
        return v1.multiply(v2);
	}
	
	public static BigDecimal chu(double v1,double v2,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal chu(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,10,BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal chu(String v1,String v2,int scale){
		BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal chu(String v1,String v2){
		BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,10,BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal chu(BigDecimal v1,BigDecimal v2){
        return v1.divide(v2,10,BigDecimal.ROUND_HALF_UP);
	}
}
