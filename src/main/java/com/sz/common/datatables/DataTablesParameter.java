package com.sz.common.datatables;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class DataTablesParameter {
	private Integer draw;
	private Integer start;//数据起始位置 0表示第一条
	private Integer length;//每页显示条数
	private String sortColumn;//排序列
	private String order;//排序规则 asc desc
	private Integer pageNo;//页号
	
	public DataTablesParameter(){}
	
	public DataTablesParameter(HttpServletRequest request){
		this.draw = Integer.parseInt(request.getParameter("draw"));
		this.start = Integer.parseInt(request.getParameter("start"));
		this.length = Integer.parseInt(request.getParameter("length"));
		this.order = request.getParameter("order[0][dir]");
		this.pageNo = start / length + 1;
		this.sortColumn=getSortColumnName(request);
	}
	
	/**
	 * 获取排序列名
	 */
	public String getSortColumnName(HttpServletRequest request){
		String sortIndex = request.getParameter("order[0][column]");
		String sortColName = request.getParameter("columns["+sortIndex+"][name]");
		return sortColName;
	}
	
	/**
	 * 获取DataTables数据grid
	 * @param totalCount  总记录数
	 * @param data  数据List
	 * @return
	 */
	public DataTables getDataTables(int totalCount,List<?> data){
		DataTables dt=new DataTables();
		dt.setDraw(getDraw());
		dt.setRecordsTotal(totalCount);
		dt.setRecordsFiltered(totalCount);
		dt.setData(data);
		return dt;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}
