package com.weknowall.cn.wuwei.widget.tab;

import android.view.View;

public interface OnSelectorChangedListener<T extends View> {
	 void OnSelectorChanged(int position, T t);
}
