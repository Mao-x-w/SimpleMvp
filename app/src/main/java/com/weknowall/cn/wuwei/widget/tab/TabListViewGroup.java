package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class TabListViewGroup extends TabViewBase<ViewGroup>{

	private ListAdapter mAdapter;
	private DataSetObserver mDataObserver = new DataSetObserver(){
		public void onChanged() {
			updateLayout();
		}

		public void onInvalidated() {
			updateLayout();
		}
	};

	public TabListViewGroup(Context context) {
		this(context, null);
	}

	public TabListViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
	}
	
	public void setAdapter(ListAdapter adapter){
		if(adapter == null)
			return;
		if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataObserver);
		}
		mAdapter = adapter;
		mAdapter.registerDataSetObserver(mDataObserver);
		updateLayout();
	}
	
	private void updateLayout(){
		removeAllViews();
		if(mAdapter == null)
			return;
		for(int i = 0; i < mAdapter.getCount(); i ++){
			View v = mAdapter.getView(i, null, this);
			addView(v);
		}
		setSelected(0);
	}

	@Override
	public boolean isInstanceOfChild(View v) {
		return true;
	}

	@Override
	public void updateChildStyle(int currentSelectId) {
		
	}
}

