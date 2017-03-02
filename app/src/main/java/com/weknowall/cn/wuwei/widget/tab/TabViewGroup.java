package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * **********************
 * Author: yu
 * Date:   2015/6/3
 * Time:   14:45
 * **********************
 */
public class TabViewGroup extends TabViewBase<ViewGroup>{


    public TabViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isInstanceOfChild(View v) {
        return v instanceof ViewGroup;
    }

    @Override
    public void updateChildStyle(int currentSelectId) {

    }
}
