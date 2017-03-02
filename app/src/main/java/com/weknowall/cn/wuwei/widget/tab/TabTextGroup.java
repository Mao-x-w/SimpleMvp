package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TabTextGroup extends TabViewBase<TextView> {

	public TabTextGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean isInstanceOfChild(View v) {
		return v instanceof TextView;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateChildStyle(int currentSelectId) {
		for (int i = 0; i < getCustomChildCount(); i++) {
			TextView item = getCustomChildViewList().get(i);
			if (selectedTextColor != 0 && unSelectedTextColor != 0)
				item.setTextColor(i == currentSelectId ? selectedTextColor : unSelectedTextColor);
			if (selectedDraw != null && unSelectedDraw != null)
				item.setBackgroundDrawable(i == currentSelectId ? selectedDraw : unSelectedDraw);
		}
	}
}
