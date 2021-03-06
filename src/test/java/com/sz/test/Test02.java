package com.sz.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.alibaba.fastjson.JSON;
import com.sz.common.util.CommonUtils;
import com.sz.common.util.SslUtils;
import com.sz.entity.Entity1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test02 {
	static int a;
	
	public void t1() {
		Entity1 e1=new Entity1();
		e1.setTitle("aa");
		t2(e1);
		System.out.println(e1.getTitle());
		
		System.out.println(a);;
	}
	
	public void t2(Entity1 e1) {
		e1.setTitle("bb");
	}
	
	public void s1() {
		String s="5";
		s2(s);
		System.out.println(s);
	}
	
	public void s2(String s) {
		s=new String("10");
	}
	
	public static void s3() {
		String s="{\"1\":{\"type\":\"5\",\"title\":\"多选下拉框\",\"placeholder\":\"请输入\",\"options\":{\"1\":{\"text\":\"选项1\",\"relation\":[]},\"2\":{\"text\":\"选项2\",\"relation\":[]}},\"is_force\":\"0\",\"is_print\":\"1\"},\"version\":1}";
		com.alibaba.fastjson.JSONObject formJsonObj=JSON.parseObject(s);
		
		System.out.println(1);
	}
	
	public static void t3() {
		String json="{\"a\":{\"1\":\"你好\",\"2\":\"不错\"},\"b\":{\"1\":\"呵呵\",\"2\":\"abcd\"}}";
		JSONObject jsonObj=JSONObject.fromObject(json);
		System.out.println(jsonObj.size());
	}
	
	public static void t4() throws Exception {
		SslUtils.ignoreSsl();
		
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File("E:/logs/nn.zip")));
		
		
		//String pic="https://t.shijiwxy.5tree.cn/esb/res/pic/12/1218/121811.jpg";
		String pic="https://t.shijiwxy.5tree.cn/edufs/download/1268112120713711616";
		URL url = new URL(pic);
		HttpURLConnection con=(HttpURLConnection)url.openConnection();
		String contentType=con.getContentType();
		System.out.println(contentType.indexOf("text/html"));
		byte[] buf = new byte[2048];
		int len;
		BufferedInputStream bufferedInputStream=new BufferedInputStream(url.openStream());
		zipOutputStream.putNextEntry(new ZipEntry("aa"+"/"+CommonUtils.getUUID()+".jpg"));
		while ((len = bufferedInputStream.read(buf)) > 0) {
			zipOutputStream.write(buf, 0, len);
		}
		bufferedInputStream.close();
		
		
		
		
		zipOutputStream.close();
	}
	
	public static void t5() throws Exception {
		try {
			String s="[\"{\\\"is_file\\\":true,\\\"id\\\":\\\"1271262166888226816\\\",\\\"file_id\\\":\\\"1271262166867255296\\\",\\\"name\\\":\\\"7467302.jpg\\\",\\\"path\\\":\\\"/我的云盘/7467302.jpg\\\",\\\"rowkey\\\":\\\"u_2521128_0_1271262166888226816\\\",\\\"rowkey_path\\\":\\\"/myYun/u_2521128_0_1271262166888226816\\\",\\\"extension\\\":\\\"jpg\\\",\\\"icon\\\":\\\"/edufs/html/plugins/oaNetdisk-path/files_icon/pic.png\\\",\\\"preview\\\":\\\"/edufs/preview/1271262166867255296\\\"}\",\"{\\\"is_file\\\":true,\\\"id\\\":\\\"1271262166946947072\\\",\\\"file_id\\\":\\\"1271262166892421120\\\",\\\"name\\\":\\\"微信图片_20200525153138.jpg\\\",\\\"path\\\":\\\"/我的云盘/微信图片_20200525153138.jpg\\\",\\\"rowkey\\\":\\\"u_2521128_0_1271262166946947072\\\",\\\"rowkey_path\\\":\\\"/myYun/u_2521128_0_1271262166946947072\\\",\\\"extension\\\":\\\"jpg\\\",\\\"icon\\\":\\\"/edufs/html/plugins/oaNetdisk-path/files_icon/pic.png\\\",\\\"preview\\\":\\\"/edufs/preview/1271262166892421120\\\"}\",\"{\\\"is_file\\\":true,\\\"id\\\":\\\"1271262169169793025\\\",\\\"file_id\\\":\\\"1271262169123655680\\\",\\\"name\\\":\\\"微信图片_20200525153132.png\\\",\\\"path\\\":\\\"/我的云盘/微信图片_20200525153132.png\\\",\\\"rowkey\\\":\\\"u_2521128_0_1271262169169793025\\\",\\\"rowkey_path\\\":\\\"/myYun/u_2521128_0_1271262169169793025\\\",\\\"extension\\\":\\\"png\\\",\\\"icon\\\":\\\"/edufs/html/plugins/oaNetdisk-path/files_icon/pic.png\\\",\\\"preview\\\":\\\"/edufs/preview/1271262169123655680\\\"}\"]";
			JSONArray array=JSONArray.fromObject(s);
			for(int i=0;i<array.size();i++) {
				System.out.println(array.getString(i));
			}
		}finally {
			System.out.println("执行了finally");
		}
		
		System.out.println(2);
	}
	
	public static void t6() throws ParseException {
		String[] arr= {"a","b","c","d","e","f","g","a1","b2","c3","d4","e5","f6","g7"};
		String beginDate="2020-06-22";
		String doDate="2020-07-07";
		
		long times=CommonUtils.stringToDate(doDate, "yyyy-MM-dd").getTime()-CommonUtils.stringToDate(beginDate, "yyyy-MM-dd").getTime();
		long days=times/(24*3600*1000);
		long arrIndex=days%arr.length;
		
		System.out.println(arr[(int)arrIndex]);
	}
	
	public static void lambda() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("aaa");
			}
		}).start();
		
		new Thread(()->{
			System.out.println("bbb");
		}).start();
	}
	
	public void t7() {
		CyclicBarrier c=new CyclicBarrier(10,()->{});
	}
	
	public void t8() throws ParseException {
		String d="2021-01-02 01:51:00";
		Date now=CommonUtils.stringToDate(d, null);
		//Date now=new Date();
		String prevdayStr=CommonUtils.getFutureDay(CommonUtils.dateFormat(now, null), "yyyy-MM-dd HH:mm:ss", -1);
		Date date=CommonUtils.stringToDate(prevdayStr, null);
		String year=CommonUtils.dateFormat(date, "yyyy");
		String month=CommonUtils.dateFormat(date, "M");
		String day=CommonUtils.dateFormat(date, "d");
		int times=CommonUtils.getWeek(date);//星期几
		
		System.out.println(0);
	}
	

	public static void main(String[] args) {
		try {
			int s=770%32;
			System.out.println(s);
			
			//new Test02().t1();
			
			//new Test02().t8();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

}
