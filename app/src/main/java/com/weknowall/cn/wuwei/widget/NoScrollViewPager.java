package com.weknowall.cn.wuwei.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * User: laomao
 * Date: 2016-09-20
 * Time: 17-06
 *
 * 不可以滑动，但是可以setCurrentItem的ViewPager。
 */

public class NoScrollViewPager extends ViewPager {
	public NoScrollViewPager(Context context) {
		super(context);
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
}
