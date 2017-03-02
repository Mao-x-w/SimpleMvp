package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

public class TabGridViewGroup extends TabViewBase<ViewGroup>{

	private ListAdapter mAdapter;
	private GridView mGridView;
	public TabGridViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGridView = new GridView(context, attrs);
	}
	
	@Override
	public boolean isInstanceOfChild(View v) {
		return true;
	}

	public void setAdapter(ListAdapter adapter){
		removeAllViews();
		if(adapter == null)
			return;
		mAdapter = adapter;
		mGridView.setAdapter(mAdapter);
		addView(mGridView, -1, -1);
		for(int i = 0; i < mAdapter.getCount(); i ++){
			View v = mAdapter.getView(i, null, this);
			addCustomChildView((ViewGroup)v);
		}
		setSelected(0);
	}

	@Override
	public void updateChildStyle(int currentSelectId) {
		
	}

}

