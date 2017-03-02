package com.weknowall.cn.wuwei.widget.recyclerview;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * **********************
 * Author: yu
 * Date:   2015/12/12
 * Time:   18:26
 * **********************
 */
public class RecyclerViewHelper {

	public static int findFirstCompleteVisibleItemPosition(RecyclerView recyclerView) {
		return findFirstVisibleItemPosition(recyclerView, true);
	}

	public static int findFirstVisibleItemPosition(RecyclerView recyclerView, boolean complete) {
		int firstItem = 0, firstCompleteItem = 0;
		if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
			GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
			firstItem = layoutManager.findFirstVisibleItemPosition();
			firstCompleteItem = layoutManager.findFirstCompletelyVisibleItemPosition();
		} else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
			LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
			firstItem = layoutManager.findFirstVisibleItemPosition();
			firstCompleteItem = layoutManager.findFirstCompletelyVisibleItemPosition();
		} else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			firstItem = layoutManager.findFirstVisibleItemPositions(null)[0];
			firstCompleteItem = layoutManager.findFirstCompletelyVisibleItemPositions(null)[0];
		}
//		Logs.d(firstItem + " " + firstCompleteItem);
		return complete ? Math.max(firstItem, firstCompleteItem) : Math.min(firstItem, firstCompleteItem == -1 ? firstItem : firstCompleteItem);
	}

	public static int findLastCompleteVisibleItemPosition(RecyclerView recyclerView) {
		return findLastVisibleItemPosition(recyclerView, true);
	}

	public static int findLastVisibleItemPosition(RecyclerView recyclerView, boolean complete) {
		int lastItem = 0, lastCompleteItem = 0;
		if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
			GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
			lastItem = layoutManager.findLastVisibleItemPosition();
			lastCompleteItem = layoutManager.findLastCompletelyVisibleItemPosition();
		} else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
			LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
			lastItem = layoutManager.findLastVisibleItemPosition();
			lastCompleteItem = layoutManager.findLastCompletelyVisibleItemPosition();
		} else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			lastItem = layoutManager.findLastVisibleItemPositions(null)[0];
			lastCompleteItem = layoutManager.findLastCompletelyVisibleItemPositions(null)[0];
		}
		return complete ? Math.max(lastItem, lastCompleteItem) : Math.min(lastItem, lastCompleteItem);
	}

	public static void scrollToTop(RecyclerView recyclerView) {
		recyclerView.stopScroll();
		recyclerView.scrollToPosition(0);
	}

	public static void scrollToTop(RecyclerView recyclerView, AppBarLayout appBarLayout){
		appBarLayout.setExpanded(true);
		scrollToTop(recyclerView);
	}
}
