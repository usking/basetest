package com.sz.test;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sz.common.util.CommonUtils;
import com.sz.entity.Entity1;
import com.sz.entity.Goods;

public class Test01 {
	
	public static void t1() {
		Date now=new Date();
		Instant insant=now.toInstant();
		LocalDateTime localDateTime=insant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		LocalDateTime time=LocalDateTime.now();
		DateTimeFormatter formater=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		String str=formater.format(time);
//		String str=time.format(formater);
		
		TemporalAdjuster temporalAdjuster=TemporalAdjusters.lastDayOfMonth();//.firstDayOfMonth();
		time=time.with(temporalAdjuster);
		
		System.out.println(time.getDayOfMonth());
	}
	
	public static void t2() {
		List<Goods> list=new ArrayList<>();
		for(int i=0;i<10;i++) {
			Goods goods=new Goods();
			goods.setId(i);
			goods.setTitle("标题"+i);
			goods.setMoney(Double.valueOf(String.format("%.2f",new Random().nextDouble())));
			list.add(goods);
		}
		
//		list.stream().peek(Goods::getTitle).forEach(System.out::println);
//		list.forEach(System.out::println);
	}
	
	public static void t3() throws ParseException {
		String d1="2018-01-23";
		String d2="2018-03-01";
		Date date1=CommonUtils.stringToDate(d1, "yyyy-MM-dd");
		Date date2=CommonUtils.stringToDate(d2, "yyyy-MM-dd");
		long s1=date1.getTime();
		long s2=date2.getTime();
		long oneDay = 1000 * 60 * 60 * 24l;
		while(s1<=s2) {
			date1=new Date(s1);
			System.out.println(CommonUtils.dateFormat(date1, "yyyy-MM-dd"));
			s1+=oneDay;
		}
	}
	
	public static void t4() throws UnsupportedEncodingException {
		String str="你好";
		String v1=Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
		
		String v2=new String(Base64.getDecoder().decode(v1),"UTF-8");
		
		
		System.out.println(v1);
		System.out.println(v2);
	}
	
	public static void t5() {
		List<Map<String,String>> errorList=new ArrayList<>();
		Map<String,String> errorMap=new HashMap<>();
		errorMap.put("errorStr", "asd");
		errorMap.put("gradeId", "1");
		errorMap.put("gradeName", "asd22你好");
		errorMap.put("clubName", "哈哈");
		errorMap.put("size", "6");
		errorMap.put("leader", "张三");
		errorMap.put("content", "没时间（啊啊）");
		errorList.add(errorMap);
		
		JSONArray json=(JSONArray)JSON.toJSON(errorList);
		
		String str=json.toJSONString();
		
		List<Map> list=JSON.parseArray(str, Map.class);
		for(Map m : list) {
			System.out.println(m.get("clubName"));
		}
		
		System.out.println(1);
	}
	
	public static void t6() {
		String s1="23c4bcec-c781-40ca-8d41-a1c498052c36";
		String s2="3b0b731b-bad2-4bfd-b305-444fc482fa21";
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		
		Set<String> set=new HashSet<>();
		set.add(s1);
		set.add(s2);
		
		System.out.println();
		
	}
	
	public static void t7() {
		int a=2;
		int b=a+++1;
		int c=++a+1;
		System.out.println(a++);
		System.out.println(a++);
		System.out.println(++a);
		System.out.println(b);
		System.out.println(c);
	}
	
	
	public static boolean isInt(String number){
		String regex="^[1-9][0-9]*$";
		return number.matches(regex);
	}
	
	public static boolean checkNumber(String number,int digit,boolean isNegative){
		String string = digit==0?"0":("1,"+digit);
		String string2 = isNegative? "-?":"";
		String regex="";
		if(digit==0) {
			regex="^" + string2 + "\\d*(\\d)?$";
		}else {
			regex="^" + string2 + "\\d*(\\d\\.\\d{"+string+"})?$";
		}
		return number.matches(regex);
	}
	
	public static void zip() throws IOException {
		File zipFile=new File("E:/a/test.zip");
		FileOutputStream fileOutputStream=new FileOutputStream(zipFile);
		
		List<File> files=new ArrayList<>();
		
		File file1=new File("E:/a/zz1.txt");
		files.add(file1);
		
		File file2=new File("E:/a/zz2.txt");
		files.add(file2);
		
		doZip(fileOutputStream,"",files);

	}
	
	public static void doZip(OutputStream outputStream,String folderName,List<File> files) throws IOException {
		if(folderName==null) {
			folderName="";
		}
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		byte[] buf = new byte[2048];
		int len;
		for(File file : files) {
			zipOutputStream.putNextEntry(new ZipEntry(folderName+"/"+file.getName()));
			BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
			while ((len = bufferedInputStream.read(buf)) > 0) {
				zipOutputStream.write(buf, 0, len);
			}
			bufferedInputStream.close();
		}
		zipOutputStream.close();
	}
	
	public static void t8(Entity1 t1) {
		String str=JSON.toJSONString(t1);
		System.out.println(str);
	}
	
	public static void t9() {
		String currentNode="1,2,3,4";
		String[] nodes=currentNode.split(",");
		nodes[nodes.length-1]=String.valueOf(Integer.parseInt(nodes[nodes.length-1])+1);
		currentNode=StringUtils.join(nodes, ",");
		System.out.println(currentNode);
	}

	public static void t10() {
		Entity1 e1=new Entity1();
		Map<String,Object> map=new HashMap<>();
		map.put("a", "111");
		map.put("b", "222");
		List<Map<String,Object>> data=new ArrayList<>();
		data.add(map);
		e1.setData(data);
		
		
		List<Map<String,Object>> list=e1.getData();
		Iterator<Map<String,Object>> iter=list.iterator();
		while(iter.hasNext()) {
			Map<String,Object> map1=iter.next();
			map1.put("c", "333");
		}
		
		System.out.println(1);
	}
	
	
	/**
	 * 获取指定日期的星期
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week=calendar.get(Calendar.DAY_OF_WEEK)-1;
		if(week==0) {//周日
			week=7;
		}
		return week;
	}
	
	/**
	 * 获取指定日期的年和第几周
	 * @param date
	 * @return
	 */
	public static String getWeekOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(date);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		int weekYear=calendar.get(Calendar.YEAR);
		int weekMonth=calendar.get(Calendar.MONTH)+1;
		if(week>40 && weekMonth==1) {//指定年的第一周如果跨年 则记为前一年的最后一周
			weekYear-=1;
		}
		String str=weekYear+","+week;
		return str;
		
	}
	
	/**
	 * 获取指定年的第几周的开始日期
	 * @param weekYear 年
	 * @param weekOfYear 指定年的第几周
	 * @return
	 */
	public static Date getBeginDateByWeekofYear(int weekYear,int weekOfYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setWeekDate(weekYear, weekOfYear, Calendar.MONDAY);
		Date date=calendar.getTime();
		return date;
	}
	
	/**
	 * 获取指定年的第几周的结束日期
	 * @param weekYear 年
	 * @param weekOfYear 指定年的第几周
	 * @return
	 */
	public static Date getEndDateByWeekofYear(int weekYear,int weekOfYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setWeekDate(weekYear, weekOfYear, Calendar.SUNDAY);
		Date date=calendar.getTime();
		return date;
	}
	
	/**
	 * 获取指定年的最大周数
	 * @param year
	 * @return
	 */
	public static int getMaxYearWeek(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		return week;
	}
	
	public static Date getDateByYearWeek(int weekYear,int weekOfYear,int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setWeekDate(weekYear, weekOfYear, week);
		Date date=calendar.getTime();
		return date;
	}
	
	public static int getCalendarWeek(int week) throws Exception {
		switch(week){
			case 1:
				return Calendar.MONDAY;
			case 2:
				return Calendar.TUESDAY;
			case 3:
				return Calendar.WEDNESDAY;
			case 4:
				return Calendar.THURSDAY;
			case 5:
				return Calendar.FRIDAY;
			case 6:
				return Calendar.SATURDAY;
			case 7:
				return Calendar.SUNDAY;
			default:
				throw new Exception("null week");
		}
	}
	
	
	public static void base64ToImg() throws IOException {
		String base64="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";
		byte[] b=Base64Utils.decodeFromString(base64);
		File file=new File("E:/logs/z.jpg");
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		BufferedImage bi = ImageIO.read(in);
		ImageIO.write(bi, "jpg", file);
	}
	
	public static boolean doTimeOverlap(List<String> dateSectionList,String pattern) throws Exception {
		boolean overlapFlag=false;
		if(pattern==null || "".equals(pattern)) {
			pattern="yyyy-MM-dd HH:mm:ss";
		}
		boolean flag=true;
		for(int i=0;i<dateSectionList.size();i++) {
			if(!flag) {
				break;
			}
			
			String dateSection1=dateSectionList.get(i);
			String s1=dateSection1.split(",")[0];
			String e1=dateSection1.split(",")[1];
			Date start1=CommonUtils.stringToDate(s1, pattern);
			Date end1=CommonUtils.stringToDate(e1, pattern);
			if(start1.getTime() > end1.getTime()) {
				throw new Exception("开始时间不能晚于结束时间");
			}
			for(int j=0;j<dateSectionList.size();j++) {
				if(j==i) {
					continue;
				}
				String dateSection2=dateSectionList.get(j);
				String s2=dateSection2.split(",")[0];
				String e2=dateSection2.split(",")[1];
				Date start2=CommonUtils.stringToDate(s2, pattern);
				Date end2=CommonUtils.stringToDate(e2, pattern);
				if(start1.getTime() <= end2.getTime() && end1.getTime() >= start2.getTime()) {
					overlapFlag=true;
					flag=false;
					break;
				}
			}
		}
		return overlapFlag;
	}
	
	
	public static void main(String[] args) {
		try {
			List<String> list=new ArrayList<>();
			list.add("08:00,13:00");
			list.add("13:01,16:00");
			list.add("09:00,10:01");
			list.add("19:00,20:01");
			
			boolean s = doTimeOverlap(list, "HH:mm");
			System.out.println(s);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

}
