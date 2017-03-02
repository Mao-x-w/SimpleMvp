package com.weknowall.app_domain.entity;

import java.util.List;

/**
 * User: laomao
 * Date: 2016-09-18
 * Time: 19-56
 */

public class PageListModel<T> {

	private int currentPage;
	private int totalPage;
	private List<T> items;

	public PageListModel() {
	}

	public PageListModel(int currentPage, int totalPage, List<T> items) {
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.items = items;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
