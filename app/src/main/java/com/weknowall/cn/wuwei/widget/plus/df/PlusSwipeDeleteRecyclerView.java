package com.weknowall.cn.wuwei.widget.plus.df;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.weknowall.cn.wuwei.widget.plus.IPlusFrame;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;


/**
 * User: JiYu
 * Date: 2016-09-20
 * Time: 21-42
 * 自定义RecyclerView
 */

public class PlusSwipeDeleteRecyclerView extends PlusRecyclerBaseView implements IPlusFrame {

	SwipeMenuRecyclerView mRecycler;

	public PlusSwipeDeleteRecyclerView(Context context) {
		this(context, null);
	}

	public PlusSwipeDeleteRecyclerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlusSwipeDeleteRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mRecycler = new SwipeMenuRecyclerView(context, attrs, defStyleAttr);
		mRecycler.setId(ID_VIEW_CONTENT);
		addView(mRecycler, -1, -1);
	}

	/**
	 * default init
	 */
	@Override
	public void initDefault() {
		super.initDefault();
		setRecycler(mRecycler);
		setLayoutManager(new LinearLayoutManager(getContext()));
	}
}
