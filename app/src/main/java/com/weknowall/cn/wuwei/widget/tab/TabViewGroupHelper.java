package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Joy
 * Date: 2016/10/14
 * Time: 10:30
 */

public class TabViewGroupHelper<T extends View> implements ITabViewGroup<T> {

	private Context context;
	private ITabViewGroup<T> rootView;

	private int selectedIndex = -1;

	private OnSelectorChangedListener<T> mListener;
	private OnSelectorChangedListener2<T> mListener2;

	private List<T> mChildList = new ArrayList<>();

	public TabViewGroupHelper(Context context, ITabViewGroup<T> rootView) {
		this.context = context;
		this.rootView = rootView;
	}

	@Override
	public boolean setSelected(int index) {
		return setSelected(index, false);
	}

	@Override
	public boolean setSelected(int index, boolean isFromUser) {
		if (index < 0) {
			for (int i = 0; i < getCustomChildViewList().size(); i++) {
				getCustomChildViewList().get(i).setSelected(false);
			}
			selectedIndex = -1;
			return false;
		}
		if (getCustomChildViewList().size() > index) {
			selectedIndex = index;
			for (int i = 0; i < getCustomChildViewList().size(); i++) {
				getCustomChildViewList().get(i).setSelected(i == index);
			}
			if (mListener != null)
				mListener.OnSelectorChanged(index, getCustomChildViewList().get(index));
			if (mListener2 != null)
				mListener2.OnSelectorChanged(index, getCustomChildViewList().get(index), isFromUser);
		}
		selectedIndex = index;
		return true;
	}

	@Override
	public int getSelected() {
		return selectedIndex;
	}

	@Override
	public void setOnSelectorChangedListener(OnSelectorChangedListener<T> mListener) {
		this.mListener = mListener;
	}

	@Override
	public OnSelectorChangedListener<T> getOnSelectorChangedListener() {
		return mListener;
	}

	@Override
	public int getCustomChildCount() {
		return mChildList.size();
	}

	@Override
	public List<T> getCustomChildViewList() {
		return mChildList;
	}

	@Override
	public void setCustomChildViewList(List<T> mViewList) {
		this.mChildList = mViewList;
	}

	@Override
	public void setOnSelectorChangedListener(OnSelectorChangedListener2<T> mListener) {
		this.mListener2 = mListener;
	}

	public Context getContext() {
		return context;
	}

	public ITabViewGroup<T> getRootView() {
		return rootView;
	}

	public void setRootView(ITabViewGroup<T> rootView) {
		this.rootView = rootView;
	}
}
