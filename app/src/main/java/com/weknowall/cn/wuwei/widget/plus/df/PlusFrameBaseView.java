package com.weknowall.cn.wuwei.widget.plus.df;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.weknowall.cn.wuwei.widget.plus.PlusFrameView;


/**
 * User: Joy
 * Date: 2016/10/22
 * Time: 12:41
 * 实现了自定义刷新样式，全局通用
 */

public class PlusFrameBaseView extends PlusFrameView {

	private PlusDefaultHeaderView mHeader;


	public PlusFrameBaseView(Context context) {
		super(context);
	}

	public PlusFrameBaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlusFrameBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void initChildView() {
		super.initChildView();
		//  添加顶部上拉加载布局
		setHeaderView(mHeader = new PlusDefaultHeaderView(getContext()));

//		setLoadingView(LayoutInflater.from(getContext()).inflate(R.layout.plus_frame_refresh_loading, this, false));
//		setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.plus_frame_refresh_empty, this, false));
//		setErrorView(LayoutInflater.from(getContext()).inflate(R.layout.plus_frame_refresh_error, this, false));
		//  默认不显示三个状态view
		hideLoadingView();
		hideEmptyView();
		hideErrorView();
	}

	public void setLastRefreshTimeKey(Object object) {
		mHeader.setLastUpdateTimeKey(object.getClass().getName());
	}

	public void setLastRefreshTimeKey(String key) {
		mHeader.setLastUpdateTimeKey(key);
	}

	public View getHeaderView(){
		return mHeaderView;
	}
}
