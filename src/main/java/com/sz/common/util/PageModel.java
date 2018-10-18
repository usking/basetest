package com.sz.common.util;

import java.util.List;

public class PageModel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String pageNo;//当前页号
	private String maxPageNo;//总页号
	private String maxPageSize;//总记录数
	private String startPageNo;//开始页号
	private String endPageNo;//结束页号
	private String pageSize;//每页显示的记录数
	
	private List<String> pageNumbers;
	
	public List<String> getPageNumbers() {
		return pageNumbers;
	}
	public void setPageNumbers(List<String> pageNumbers) {
		this.pageNumbers = pageNumbers;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getStartPageNo() {
		return startPageNo;
	}
	public void setStartPageNo(String startPageNo) {
		this.startPageNo = startPageNo;
	}
	public String getEndPageNo() {
		return endPageNo;
	}
	public void setEndPageNo(String endPageNo) {
		this.endPageNo = endPageNo;
	}
	private List<?> list;
	
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getMaxPageNo() {
		return maxPageNo;
	}
	public void setMaxPageNo(String maxPageNo) {
		this.maxPageNo = maxPageNo;
	}
	public String getMaxPageSize() {
		return maxPageSize;
	}
	public void setMaxPageSize(String maxPageSize) {
		this.maxPageSize = maxPageSize;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	
}
