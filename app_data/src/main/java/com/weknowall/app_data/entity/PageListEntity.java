package com.weknowall.app_data.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * User: laomao
 * Date: 2016-09-18
 * Time: 19-57
 * 分页列表专用Entity model
 */

public class PageListEntity<T> {

	/**
	 * 电商模块用
	 */
	private String code;
	private String msg;

	@JSONField(name = "current_page")
	private String currentPage;
	@JSONField(name = "total_page")
	private String totalPage;
	@JSONField(name = "items")
	private List<T> items;

	public PageListEntity() {
	}

	public PageListEntity(String currentPage, String totalPage, List<T> items) {
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.items = items;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
