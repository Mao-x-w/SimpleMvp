package com.weknowall.cn.wuwei.widget.plus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.weknowall.cn.wuwei.utils.Logs;

/**
 * User: JiYu
 * Date: 2016-09-24
 * Time: 13-34
 */

public class PlusFrameView extends CanRefreshLayout implements IPlusFrame {

	protected static final int ID_VIEW_HEADER = com.canyinghao.canrefresh.R.id.can_refresh_header;
	protected static final int ID_VIEW_FOOTER = com.canyinghao.canrefresh.R.id.can_refresh_footer;
	protected static final int ID_VIEW_CONTENT = com.canyinghao.canrefresh.R.id.can_content_view;

	private View mLoadingView;
	private View mErrorView;
	private View mEmptyView;

	private LoadingType mLoadingType = LoadingType.Get;
	private LoadingState mLoadingState = LoadingState.None;

	private boolean mRefreshEnable = true;
	private boolean mLoadMoreEnable = true;

	private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;
	private OnErrorClickListener mErrorClickListener;

	public PlusFrameView(Context context) {
		this(context, null);
	}

	public PlusFrameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlusFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initChildView();
		setRefreshEnable(true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initDefault();
	}

	/**
	 * 初始化子View，loadingView、emptyView、errorView等
	 */
	protected void initChildView() {

	}

	/**
	 * 当页面加载完毕后初始化一些基本信息
	 */
	public void initDefault() {

	}

	/**
	 * 设置下拉刷新头布局
	 * @param headerView headerView
	 */
	public void setHeaderView(View headerView) {
		if (this.mHeaderView != null) {
			removeView(this.mHeaderView);
		}
		this.mHeaderView = headerView;
		this.mHeaderView.setId(ID_VIEW_HEADER);
		addView(this.mHeaderView, -1, -2);
	}

	/**
	 * 设置上拉加载尾布局
	 * @param footerView footerView
	 */
	public void setFooterView(View footerView) {
		if (this.mFooterView != null) {
			removeView(this.mFooterView);
		}
		this.mFooterView = footerView;
		this.mFooterView.setId(ID_VIEW_FOOTER);
		addView(this.mFooterView, -1, -2);
	}

	/**
	 * 设置没数据时显示的view
	 * @param view view
	 */
	public void setEmptyView(View view) {
		if (mEmptyView != null) {
			removeView(mEmptyView);
		}
		if (view != null) {
			mEmptyView = view;
			addView(view, -1, -1);
		}
	}

	/**
	 * 设置出错时显示的view
	 * @param view view
	 */
	public void setErrorView(View view) {
		if (mErrorView != null) {
			removeView(mErrorView);
		}
		if (view != null) {
			mErrorView = view;
			addView(view, -1, -1);
			mErrorView.setOnClickListener(v -> {
				if (mErrorClickListener != null) {
					mErrorClickListener.onErrorClick();
				}
			});
		}
	}

	/**
	 * 设置加载时显示的view
	 * @param view view
	 */
	public void setLoadingView(View view) {
		if (mLoadingView != null) {
			removeView(mLoadingView);
		}
		if (view != null) {
			mLoadingView = view;
			addView(view, -1, -1);
		}
	}

	/**
	 * 设置是否支持下拉刷新
	 */
	public void setRefreshEnable(boolean refreshable) {
		this.mRefreshEnable = refreshable;
		super.setRefreshEnabled(refreshable);
	}

	/**
	 * 获取是否支持下拉刷新
	 */
	public boolean canRefresh() {
		return this.mRefreshEnable;
	}

	/**
	 * 设置是否支持加载更多
	 */
	public void setLoadMoreEnable(boolean loadMoreEnable) {
		this.mLoadMoreEnable = loadMoreEnable;
	}

	/**
	 * 获取是否支持加载更多
	 */
	public boolean canLoadMore() {
		return this.mLoadMoreEnable;
	}

	@Override
	@Deprecated
	public void setOnRefreshListener(@NonNull OnRefreshListener mOnRefreshListener) {
		super.setOnRefreshListener(mOnRefreshListener);
	}

	@Override
	@Deprecated
	public void setOnLoadMoreListener(@NonNull OnLoadMoreListener mOnLoadMoreListener) {
		super.setOnLoadMoreListener(mOnLoadMoreListener);
	}

	public void setOnPlusRefreshListener(OnRefreshListener refreshListener) {
		this.mRefreshListener = refreshListener;
		if (mRefreshListener != null)
			super.setOnRefreshListener(() -> mRefreshListener.onRefresh());
	}

	public void setOnPlusLoadMoreListener(OnLoadMoreListener loadMoreListener) {
		this.mLoadMoreListener = loadMoreListener;
		if (mLoadMoreListener != null)
			super.setOnLoadMoreListener(() -> mLoadMoreListener.onLoadMore());
	}

	/**
	 * 设置ErrorView的点击事件
	 * @param errorClickListener errorClickListener
	 */
	public void setOnPlusErrorClickListener(OnErrorClickListener errorClickListener) {
		this.mErrorClickListener = errorClickListener;
		if (mErrorView != null) {
			mErrorView.setOnClickListener(v -> errorClickListener.onErrorClick());
		}
	}

	/**
	 * 设置当前的加载状态
	 * @param state 加载状态{@link LoadingState}
	 */
	public void setLoadingState(LoadingState state) {
		this.mLoadingState = state;
		onLoadingStateChanged(mLoadingState);
	}

	/**
	 * 获取当前的加载状态{@link LoadingState}
	 */
	public LoadingState getLoadingState() {
		return mLoadingState;
	}

	/**
	 * 获取当前的加载类型{@link LoadingType}
	 */
	public LoadingType getLoadingType() {
		return mLoadingType;
	}

	/**
	 * 当加载状态发生改变时
	 * @param state 加载状态{@link LoadingState}
	 */
	protected void onLoadingStateChanged(LoadingState state) {
		switch (state) {
			case None:
				hideLoadingView();
				hideEmptyView();
				hideErrorView();
				break;
			case Empty:
				hideLoadingView();
				showEmptyView();
				hideErrorView();
				break;
			case Error:
				hideLoadingView();
				hideEmptyView();
				showErrorView();
				break;
			case Loading:
			case NoMore:
				hideLoadingView();
				hideEmptyView();
				hideErrorView();
				break;
		}
	}

	@Override
	public void notifySuccess() {
		notifyDataSetChanged();
		setLoadingState(LoadingState.Loading);
	}

	/**
	 * 列表加载完成时，即没有更多数据可加载
	 */
	@Override
	public void notifyFinish() {
		notifyDataSetChanged();
		setLoadingState(LoadingState.NoMore);
	}

	@Override
	public void notifyError() {
		notifyDataSetChanged();
		setLoadingState(LoadingState.Error);
	}

	@Override
	public void notifyEmpty() {
		notifyDataSetChanged();
		setLoadingState(LoadingState.Empty);
	}

	/**
	 * 刷新当前的加载状态
	 * @param type 加载状态{@link LoadingType}
	 */
	@Override
	public void notifyLoading(LoadingType type) {
		this.mLoadingType = type;
		setLoadingState(type == LoadingType.More ? LoadingState.Loading : LoadingState.None);
		if (type == LoadingType.Get) {
			showLoadingView();

		}
		//		notifyDataSetChanged();
	}

	/**
	 * 通知数据状态发生改变
	 */
	protected void notifyDataSetChanged() {
		refreshComplete();
		hideLoadingView();
	}

	protected synchronized void showLoadingView() {
		if (mLoadingView != null && mLoadingView.getVisibility() != VISIBLE) {
			mLoadingView.setVisibility(VISIBLE);
			Logs.d("showLoadingView");
		}
	}

	protected synchronized void hideLoadingView() {
		if (mLoadingView != null && mLoadingView.getVisibility() != GONE) {
			mLoadingView.setVisibility(GONE);
			Logs.d("hideLoadingView");
		}
	}

	protected synchronized void showErrorView() {
		if (mErrorView != null && mErrorView.getVisibility() != VISIBLE) {
			mErrorView.setVisibility(VISIBLE);
			Logs.d("showErrorView");
		}
	}

	protected synchronized void hideErrorView() {
		if (mErrorView != null && mErrorView.getVisibility() != GONE) {
			mErrorView.setVisibility(GONE);
			Logs.d("hideErrorView");
		}
	}

	protected synchronized void showEmptyView() {
		if (mEmptyView != null && mEmptyView.getVisibility() != VISIBLE) {
			mEmptyView.setVisibility(VISIBLE);
			Logs.d("showEmptyView");
		}
	}

	protected synchronized void hideEmptyView() {
		if (mEmptyView != null && mEmptyView.getVisibility() != GONE) {
			mEmptyView.setVisibility(GONE);
			Logs.d("hideEmptyView");
		}
	}

	public OnLoadMoreListener getLoadMoreListener() {
		return mLoadMoreListener;
	}
}
