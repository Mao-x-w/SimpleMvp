package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TabHorizontalTextGroup extends TabHorizontalViewGroupBase<TextView>{

	public TabHorizontalTextGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public TabViewBase<TextView> onCreateParent(Context context,
			AttributeSet attrs) {
		return new TabTextGroup(context, attrs);
	}


}

