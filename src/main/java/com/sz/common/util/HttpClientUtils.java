package com.sz.common.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	private static final String ENCODING = "UTF-8";
    private static HttpClient client = null;
    private static HttpClient clientSSL = null;
    
    static{
    	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    	cm.setMaxTotal(200);
    	cm.setDefaultMaxPerRoute(200);
    	client = HttpClients.custom().setConnectionManager(cm).build();
    	
    	try {
    		SSLContext sslContext = SSLContext.getInstance("SSL");
    		sslContext.init(null, new TrustManager[] { new MyX509TrustManager() }, new SecureRandom());
    		clientSSL = HttpClients.custom().setSslcontext(sslContext).build();
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static String get(String url,Map<String,String> paramMap) throws ClientProtocolException, IOException {
    	return get(url,paramMap,null);
    }
    
    public static String get(String url,Map<String,String> paramMap,Map<String,String> headerMap) throws ClientProtocolException, IOException {
    	HttpClient httpClient=null;
    	if(url.startsWith("https")) {
    		httpClient=clientSSL;
    	}else {
    		httpClient=client;
    	}
    	if(paramMap!=null && paramMap.size()>0) {
    		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
    		for(Entry<String,String> entry : paramMap.entrySet()) {
    			nameValuePairList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
    		}
    		String queryString = URLEncodedUtils.format(nameValuePairList, ENCODING);
    		if (url.indexOf("?") > -1) {
    			url += "&" + queryString;
    		}else {
    			url += "?" + queryString;
    		}
    	}
    	HttpGet httpGet = new HttpGet(url);
    	if(headerMap!=null && headerMap.size()>0) {
			for(Entry<String,String> entry : headerMap.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
    	try {
    		HttpResponse rsp = httpClient.execute(httpGet);
    		String result=EntityUtils.toString(rsp.getEntity(),ENCODING);
    		return result;
    	}finally {
    		httpGet.abort();
    	}
    }
    
    
    public static String post(String url,Map<String,String> paramMap) throws ClientProtocolException, IOException {
    	return post(url,paramMap,null);
    }
    
    public static String post(String url,Map<String,String> paramMap,Map<String,String> headerMap) throws ClientProtocolException, IOException {
    	HttpClient httpClient=null;
    	if(url.startsWith("https")) {
    		httpClient=clientSSL;
    	}else {
    		httpClient=client;
    	}
    	HttpPost httpPost = new HttpPost(url);
		if(paramMap!=null && paramMap.size()>0) {
    		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
    		for(Entry<String,String> entry : paramMap.entrySet()) {
    			nameValuePairList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
    		}
    		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, ENCODING));
    	}
		
		if(headerMap!=null && headerMap.size()>0) {
			for(Entry<String,String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
    	try {	
    		HttpResponse rsp = httpClient.execute(httpPost);
    		String result=EntityUtils.toString(rsp.getEntity(),ENCODING);
    		return result;
    	}finally {
    		httpPost.abort();
    	}
    }
    
    private static class MyX509TrustManager implements X509TrustManager {
        /**
         * 检查客户端证书，若不信任，抛出异常
         */
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        /**
         * 检查服务端证书，若不信任，抛出异常，反之，若不抛出异常，则表示信任（空方法代表信任所有的服务端证书）
         */
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        /**
         * 返回受信任的X509证书数组
         */
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
