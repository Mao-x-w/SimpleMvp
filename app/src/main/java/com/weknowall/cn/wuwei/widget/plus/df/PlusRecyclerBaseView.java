package com.weknowall.cn.wuwei.widget.plus.df;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.weknowall.cn.wuwei.utils.Logs;
import com.weknowall.cn.wuwei.widget.plus.LoadingState;
import com.weknowall.cn.wuwei.widget.plus.LoadingType;
import com.weknowall.cn.wuwei.widget.plus.PlusRecyclerHeaderFooter;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;

/**
 * User: JiYu
 * Date: 2016-09-24
 * Time: 15-48
 * RecyclerView版PlusView，重新实现自己的加载更多样式
 */

public class PlusRecyclerBaseView extends PlusFrameBaseView {

	private static final int LOADING_MORE_HINT_LOADING = com.weknowall.cn.wuwei.R.string.plus_refresh_footer_loading;
	private static final int LOADING_MORE_HINT_ERROR = com.weknowall.cn.wuwei.R.string.plus_refresh_footer_error;
	private static final int LOADING_MORE_HINT_NOMORE = com.weknowall.cn.wuwei.R.string.plus_refresh_footer_nomore;

	View mFooterLayout;
	TextView mFooterText;
	View mFooterProgress;
	View mFooterNoMore;
	PlusRecyclerHeaderFooter mFooter;

	private RecyclerView mRecycler;

	public PlusRecyclerBaseView(Context context) {
		super(context);
	}

	public PlusRecyclerBaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlusRecyclerBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initChildView() {
		super.initChildView();
		//  添加底部加载更多布局
		View footer = LayoutInflater.from(getContext()).inflate(com.weknowall.cn.wuwei.R.layout.plus_recycler_footerview, this, false);
		addView(footer, -1, -2);

		mFooter = (PlusRecyclerHeaderFooter) findViewById(com.weknowall.cn.wuwei.R.id.plus_refresh_footer);
		mFooterLayout = findViewById(com.weknowall.cn.wuwei.R.id.plus_footer_layout);
		mFooterProgress = findViewById(com.weknowall.cn.wuwei.R.id.plus_footer_progress);
		mFooterText = (TextView) findViewById(com.weknowall.cn.wuwei.R.id.plus_footer_hint);
		mFooterNoMore = findViewById(com.weknowall.cn.wuwei.R.id.plus_footer_image);
	}

	/**
	 * default init
	 */
	@Override
	public void initDefault() {
		super.initDefault();
		mFooter.setLoadEnable(canLoadMore());
	}

	@Override
	public void setLoadMoreEnable(boolean loadMoreEnable) {
		super.setLoadMoreEnable(loadMoreEnable);
		mFooter.setLoadEnable(loadMoreEnable);
		if (!loadMoreEnable) {
			hideFooterView();
		}
	}

	@Override
	public void setOnPlusLoadMoreListener(OnLoadMoreListener loadMoreListener) {
		super.setOnPlusLoadMoreListener(loadMoreListener);
		mFooter.setLoadMoreListener(getLoadMoreListener()::onLoadMore);
	}

	public void setAdapter(AdapterPlus adapter) {
		if (mRecycler != null)
			mRecycler.setAdapter(adapter);
	}

	public AdapterPlus getAdapter() {
		return (AdapterPlus) mRecycler.getAdapter();
	}

	public void setLayoutManager(@NonNull RecyclerView.LayoutManager manager) {
		if (mRecycler != null) {
			mRecycler.setLayoutManager(manager);
			mFooter.attachTo(mRecycler, false);
            hideFooterView();
		}
	}

	public void setRecycler(RecyclerView recycler) {
		this.mRecycler = recycler;
	}

	public RecyclerView getRecycler() {
		return mRecycler;
	}

	protected PlusRecyclerHeaderFooter getRecyclerFooterView() {
		return mFooter;
	}

	@Override
	public void notifyLoading(LoadingType type) {
		super.notifyLoading(type);
        if (type == LoadingType.Get && getAdapter() != null) {
			getAdapter().clear();
		}
	}

	/**
	 * 通知数据状态发生改变
	 */
	protected void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		mFooter.loadMoreComplete();
	}

	/**
	 * 当加载状态发生改变时
	 */
	@Override
	protected void onLoadingStateChanged(LoadingState state) {
		Logs.d(state);
		super.onLoadingStateChanged(state);
		//  如果是出错
		switch (state) {
			case Error:
				switch (getLoadingType()) {
					case Refresh:
						if (getAdapter() != null && getAdapter().getList() != null) {
							if (getAdapter().getList().size() > 0) {
								hideErrorView();
							} else {
								showErrorView();
							}
						}
						break;
					case More:
						hideErrorView();
						break;
				}
				break;
		}
		changeLoadingMoreState(state);
	}

	/**
	 * 改变加载更多提示View显示效果
	 */
	private void changeLoadingMoreState(LoadingState state) {
		switch (state) {
			case None:
			case Empty:
				mFooter.setLoadEnable(false);
				hideFooterView();
				break;
			case Loading:
				if (canLoadMore()) {
					showFooterView();
					mFooter.setLoadEnable(true);
					mFooterNoMore.setVisibility(GONE);
					mFooterProgress.setVisibility(VISIBLE);
					mFooterText.setVisibility(VISIBLE);
					mFooterText.setText(LOADING_MORE_HINT_LOADING);
				} else {
					hideFooterView();
				}
				break;
			case Error:
				if (getLoadingType() == LoadingType.Get) {
					hideFooterView();
				} else if (canLoadMore()) {
					showFooterView();
					mFooterNoMore.setVisibility(GONE);
					mFooterProgress.setVisibility(GONE);
					mFooterText.setVisibility(VISIBLE);
					mFooterText.setText(LOADING_MORE_HINT_ERROR);
				}
				break;
			case NoMore:
				//  如果当前是正在加载更多中，则显示底部view，否则隐藏底部view
				mFooter.setLoadEnable(false);
				if (getLoadingType() == LoadingType.More && canLoadMore()) {
					showFooterView();
					mFooterNoMore.setVisibility(VISIBLE);
					mFooterProgress.setVisibility(GONE);
					mFooterText.setVisibility(GONE);
					mFooterText.setText(LOADING_MORE_HINT_NOMORE);
				} else {
					hideFooterView();
				}
				break;
		}
	}

	protected void showFooterView() {
		mFooterLayout.setVisibility(VISIBLE);
	}

	protected void hideFooterView() {
		mFooterLayout.setVisibility(INVISIBLE);
	}
}
