package com.sz.common.test;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

public class Test {

	public static void main(String[] args) {
		test1();

	}
	
	public static void test1(){
		String str="abc\r\ndjfskjl \"<script> <html><a href=''>abcdefg</a></html>   你好";
		String s1=StringEscapeUtils.escapeHtml4(str);
		String s2=HtmlUtils.htmlEscape(str);
		String s3=JavaScriptUtils.javaScriptEscape(str);
		String s4=StringEscapeUtils.escapeEcmaScript(str);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
	}

}
