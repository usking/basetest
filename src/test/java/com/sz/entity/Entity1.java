package com.sz.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Entity1 {
	private Integer id;
	private String title;
	
	List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
