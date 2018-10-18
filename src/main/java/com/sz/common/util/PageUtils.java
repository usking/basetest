package com.sz.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils {
	private final static int SHOWPAGE = 9;//最好是单数
	/**
	 * 根据传入的当前页号和总页号 算出起始页号和结束页号
	 * @param pageNo
	 * @param maxPageNo
	 * @return
	 */
	public static Map<String,String> getStartEndNo(int pageNo,int maxPageNo){
		int startPageNo=1;
		int endPageNo=1;
		Map<String,String> map=new HashMap<String,String>();
		if(maxPageNo<=SHOWPAGE){
			startPageNo=1;
			endPageNo=maxPageNo;
		}else{
			if(pageNo-(SHOWPAGE-1)/2 <= 0){
				//前面不够
				startPageNo=1;
				endPageNo=SHOWPAGE;
			}else if(pageNo+(SHOWPAGE-1)/2 > maxPageNo){
				//后面不够
				endPageNo=maxPageNo;
				startPageNo=maxPageNo-(SHOWPAGE-1);
			}else{
				//前后都够
				startPageNo=pageNo-(SHOWPAGE-1)/2;
				endPageNo=pageNo+(SHOWPAGE-1)/2;
			}
		}
		map.put("startPageNo", String.valueOf(startPageNo));
		map.put("endPageNo", String.valueOf(endPageNo));
		return map;
	}
	
	/**
	 * 传入记录总数和每页显示记录数算出总页数
	 * @param maxSize
	 * @param pageSize
	 * @return
	 */
	public static int getMaxPageNo(int maxPageSize,int pageSize){
		int maxPageNo=0;
		if(maxPageSize%pageSize==0){
			maxPageNo=maxPageSize/pageSize;
		}else{
			maxPageNo=maxPageSize/pageSize+1;
		}
		return maxPageNo;
	}
	
	/**
	 * 生成Page对象
	 * @param pageNo  当前页号
	 * @param pageSize  每页显示记录数
	 * @param maxPageSize  总记录数
	 * @param list  数据List
	 * @return  Page对象
	 */
	public static PageModel doPage(int pageNo,int pageSize,int maxPageSize,List list){
		if(pageSize==0){pageSize=1;}
		int maxPageNo=PageUtils.getMaxPageNo(maxPageSize, pageSize);
		Map<String,String> pageMap=PageUtils.getStartEndNo(pageNo, maxPageNo);
		PageModel page=new PageModel();
		page.setStartPageNo(pageMap.get("startPageNo"));//开始页号
		page.setEndPageNo(pageMap.get("endPageNo"));//结束页号
		page.setMaxPageNo(String.valueOf(maxPageNo));//最大页号
		page.setMaxPageSize(String.valueOf(maxPageSize));//总记录数
		page.setPageNo(String.valueOf(pageNo));//当前页号
		page.setList(list);//数据list
		page.setPageSize(String.valueOf(pageSize));//每页显示记录数
		return page;
	}
}
