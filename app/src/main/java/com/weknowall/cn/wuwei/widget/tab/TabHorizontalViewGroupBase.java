package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.List;

public abstract class TabHorizontalViewGroupBase<T extends View> extends HorizontalScrollView implements
		ITabViewGroup<T> {

	private CustomViewAdapter<T> mAdapter;
	private TabViewBase<T> mParent;
	private OnSelectorChangedListener<T> mListener;
	private OnSelectorChangedListener2<T> mListener2;
	private DataSetObserver mDataObserver = new DataSetObserver(){
		public void onChanged() {
			updateLayout();
		};

		public void onInvalidated() {
			updateLayout();
		};
	};



	public TabHorizontalViewGroupBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(false);
		mParent = onCreateParent(context, attrs);
		mParent.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams params = new LayoutParams(context,attrs);
		addView(mParent, params);
		mParent.setOnSelectorChangedListener(new OnSelectorChangedListener<T>() {

			@Override
			public void OnSelectorChanged(int position, T t) {
				updateScroll(position);
				if(mListener != null)
					mListener.OnSelectorChanged(position, t);
			}
		});
		mParent.setOnSelectorChangedListener(new OnSelectorChangedListener2<T>() {
			@Override
			public void OnSelectorChanged(int position, T t,
					boolean isFromUser) {
				if(mListener2 != null)
					mListener2.OnSelectorChanged(position, t, isFromUser);
			}
		});
	}
	//
	//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  {  
	//		mParent.measure(MeasureSpec.AT_MOST, heightMeasureSpec);
	//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
	//	} 
	//		
	//	@Override
	//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	//		int width = 0;
	//		for(int i = 0; i < mParent.getCustomChildCount(); i ++){
	//			final View child = mParent.getCustomChildViewList().get(i);
	//			width += child.getMeasuredWidth();
	//		}
	//		Logger.log("CustomHorzotalViewGroupBase width is "+width);
	//		mParent.layout(l, t, l + width, t + mParent.getMeasuredHeight());
	//	}

	public abstract TabViewBase<T> onCreateParent(Context context, AttributeSet attrs);

	public void setAdapter(CustomViewAdapter<T> adapter){
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
		mParent.removeAllViews();
		if(mAdapter == null)
			return;
		for(int i = 0; i < mAdapter.getCount(); i ++){
			View v = mAdapter.getView(i, null, mParent);
			mParent.addView(v);
		}
		setSelected(0);
	}

	@Override
	public boolean setSelected(int index) {
		return mParent.setSelected(index);
	}

	@Override
	public boolean setSelected(int index, boolean isFromUser) {
		return mParent.setSelected(index, isFromUser);
	}

	private void updateScroll(int currentIndex){
		if(mParent.getCustomChildCount() > 1){
			int moveTo =  (int) mParent.getCustomChildViewList().get(currentIndex).getLeft() - 
					mParent.getCustomChildViewList().get(1).getLeft();
			smoothScrollTo(moveTo, 0);
		}
	}
	@Override
	public void setOnSelectorChangedListener(
			OnSelectorChangedListener2<T> mListener) {
		this.mListener2 = mListener;
	}

	@Override
	public int getSelected() {
		return mParent.getSelected();
	}

	@Override
	public void setOnSelectorChangedListener(
			OnSelectorChangedListener<T> mListener) {
		this.mListener = mListener;
		//mParent.setOnSelectorChangedListener(mListener);
	}

	@Override
	public OnSelectorChangedListener<T> getOnSelectorChangedListener() {
		return mListener;
	}

	@Override
	public int getCustomChildCount() {
		return mParent.getCustomChildCount();
	}

	@Override
	public List<T> getCustomChildViewList() {
		return mParent.getCustomChildViewList();
	}

	@Override
	public void setCustomChildViewList(List<T> mViewList) {
		mParent.setCustomChildViewList(mViewList);
	}

	public static abstract class CustomViewAdapter<T> extends BaseAdapter{

		public abstract T getView(int position, T convertView, ViewGroup parent);
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getView(position, convertView, parent);
		}		
	}
}
