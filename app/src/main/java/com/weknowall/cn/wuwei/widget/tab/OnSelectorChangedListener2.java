package com.weknowall.cn.wuwei.widget.tab;

import android.view.View;

public interface OnSelectorChangedListener2<T extends View> {
	 void OnSelectorChanged(int position, T t, boolean isFromUser);
}
