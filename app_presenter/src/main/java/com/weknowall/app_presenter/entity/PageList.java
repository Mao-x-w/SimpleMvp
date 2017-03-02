package com.weknowall.app_presenter.entity;

import java.util.List;

/**
 * User: laomao
 * Date: 2016-09-19
 * Time: 16-48
 */

public class PageList<T> {

	private int currentPage;
	private int totalPage;
	private List<T> items;

	public PageList() {
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
