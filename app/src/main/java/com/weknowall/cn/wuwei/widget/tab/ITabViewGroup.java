package com.weknowall.cn.wuwei.widget.tab;

import android.view.View;

import java.util.List;

interface ITabViewGroup<T extends View> {

	boolean setSelected(int index);

	boolean setSelected(int index, boolean isFromUser);

	int getSelected();

	void setOnSelectorChangedListener(OnSelectorChangedListener<T> mListener);

	OnSelectorChangedListener<T> getOnSelectorChangedListener();

	int getCustomChildCount();

	List<T> getCustomChildViewList();

	void setCustomChildViewList(List<T> mViewList);

	void setOnSelectorChangedListener(OnSelectorChangedListener2<T> mListener);
}
