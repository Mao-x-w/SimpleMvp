package com.weknowall.cn.wuwei.widget.plus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.canyinghao.canrecyclerview.CanRecyclerViewHeaderFooter;
import com.weknowall.app_common.utils.DeviceHelper;
import com.nineoldandroids.view.ViewHelper;

import java.util.Arrays;

/**
 * User: Joy
 * Date: 2016/10/8
 * Time: 17:57
 */

public class PlusRecyclerHeaderFooter extends FrameLayout {

	//    是否依附在RecyclerView上
	private boolean isAttached;
	//    是头部还是底部
	private boolean isHeader = true;

	//   RecyclerView是否颠倒
	private boolean isReversed;
	//    RecyclerView是否水平
	private boolean isVertical;
	//  用以缓存是否调用加载
	private boolean isCanLoad;
	//  设置是否可加载
	private boolean isLoadEnable = true;

	//  是否加载完成
	private boolean isLoadComplete = true;

	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	//  给RecyclerView的头部底部加Decoration
	private PlusItemDecoration decoration;
	//  loadmore的监听事件
	private CanRecyclerViewHeaderFooter.OnLoadMoreListener loadMoreListener;

	// 滑动监听
	private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			onScrollChanged();
		}
	};

	//  依附监听
	private RecyclerView.OnChildAttachStateChangeListener onAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
		@Override
		public void onChildViewAttachedToWindow(View view) {
		}

		@Override
		public void onChildViewDetachedFromWindow(View view) {
			post(() -> {
				if (!recyclerView.isComputingLayout()) {
					recyclerView.invalidateItemDecorations();
				}
				onScrollChanged();
			});
		}
	};
	private OnOffsetChangedListener onOffsetChangedListener;

	public PlusRecyclerHeaderFooter(Context context) {
		super(context);
	}

	public PlusRecyclerHeaderFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlusRecyclerHeaderFooter(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		Logs.d("");
	}

	/**
	 * 重写该方法，更新头部底部宽高
	 * @param changed
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed && isAttached) {
			if (decoration != null) {
				int[] wh = measureWidthAndHeight();
				decoration.setWidth(wh[0]).setHeight(wh[1]);
				recyclerView.invalidateItemDecorations();
			}
			onScrollChanged();
		}
		super.onLayout(changed, l, t, r, b);
	}

	/**
	 * 依附的方法
	 * @param recycler
	 * @param isHeader
	 */
	public void attachTo(@NonNull final RecyclerView recycler, boolean isHeader) {
		if (recycler.getLayoutManager() == null) {
			throw new IllegalStateException("no LayoutManager.");
		}
		this.isHeader = isHeader;
		recyclerView = recycler;
		layoutManager = recyclerView.getLayoutManager();

		initLayoutManager();

		isAttached = true;

		if (decoration != null) {
			recyclerView.removeItemDecoration(decoration);
		}
		decoration = new PlusItemDecoration(layoutManager).setIsHeader(isHeader);
		recyclerView.addItemDecoration(decoration);
		// 如果当前view的宽高不为0的话，则刷新对应的Decoration的宽和高并刷新父RecyclerView的ItemDecorations;
		if (getWidth() > 0 && getHeight() > 0) {
			int[] wh = measureWidthAndHeight();
			decoration.setWidth(wh[0]).setHeight(wh[1]);
			recyclerView.invalidateItemDecorations();
		}
		requestLayout();

		recyclerView.removeOnScrollListener(onScrollListener);
		recyclerView.addOnScrollListener(onScrollListener);

		recyclerView.removeOnChildAttachStateChangeListener(onAttachListener);
		recyclerView.addOnChildAttachStateChangeListener(onAttachListener);
	}

	/**
	 * 是否可以加载，没有更多时可以调用
	 * @param loadEnable
	 */
	public void setLoadEnable(boolean loadEnable) {
		isLoadEnable = loadEnable;
	}

	/**
	 * 设置加载监听事件
	 * @param loadMoreListener
	 */
	public void setLoadMoreListener(CanRecyclerViewHeaderFooter.OnLoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
	}

	/**
	 * 加载完成时调用，避免重复加载
	 */
	public void loadMoreComplete() {
		this.isLoadComplete = true;
	}


	/**
	 * 设置偏移量改变监听
	 * @param onOffsetChangedListener onOffsetChangedListener
	 */
	public void setOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
		this.onOffsetChangedListener = onOffsetChangedListener;
	}


	/**
	 * 通过layoutManager获取各种属性值
	 */
	private void initLayoutManager() {
		if (layoutManager instanceof GridLayoutManager) {
			GridLayoutManager grid = (GridLayoutManager) layoutManager;
			this.isReversed = grid.getReverseLayout();
			this.isVertical = grid.getOrientation() == LinearLayoutManager.VERTICAL;
		} else if (layoutManager instanceof LinearLayoutManager) {
			LinearLayoutManager linear = (LinearLayoutManager) layoutManager;
			this.isReversed = linear.getReverseLayout();
			this.isVertical = linear.getOrientation() == LinearLayoutManager.VERTICAL;
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager staggeredGrid = (StaggeredGridLayoutManager) layoutManager;
			this.isReversed = staggeredGrid.getReverseLayout();
			this.isVertical = staggeredGrid.getOrientation() == LinearLayoutManager.VERTICAL;
		}
	}

	private int[] measureWidthAndHeight() {
		int vertical = 0;
		int horizontal = 0;
		if (getLayoutParams() instanceof MarginLayoutParams) {
			final MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
			vertical = layoutParams.topMargin + layoutParams.bottomMargin;
			horizontal = layoutParams.leftMargin + layoutParams.rightMargin;
		}
		//		decoration.setHeight(getHeight() + vertical).setWidth(getWidth() + horizontal);
		return new int[]{getWidth() + horizontal, getHeight() + vertical};
	}


	/**
	 * 滚动时移动头部底部
	 */
	private void onScrollChanged() {
		if (isHeader) {
			boolean isFirst = hasItems() && isFirstRowVisible();
			translationXY(isFirst);
		} else {
			boolean isLast = hasItems() && isLastRowVisible();
			translationXY(isLast);
		}
	}


	/**
	 * 移动的方法
	 */
	private void translationXY(boolean isFirst) {
		setVisibility(isFirst ? VISIBLE : INVISIBLE);
		if (isFirst) {
			if (isLoadEnable && isLoadComplete && isCanLoad && loadMoreListener != null) {
				loadMoreListener.onLoadMore();
				isCanLoad = false;
				isLoadComplete = false;
			}
			int first = calculateTranslation();
//			Logs.d(" rang " + getScrollRange() + " offset " + getScrollOffset() + " size " + getSize());
//			Logs.d("screen " + DeviceHelper.getScreenHeight(getContext()) + " top " + getTop());
			if (onOffsetChangedListener != null) {
				onOffsetChangedListener.onOffsetChanged(this, DeviceHelper.getScreenWidth() - first);
			}
			if (isVertical) {
				ViewHelper.setTranslationY(this, first);
			} else {
				ViewHelper.setTranslationX(this, first);
			}
		} else {
			isCanLoad = true;
		}
	}

	/**
	 * 判断头部底部进行计算距离
	 * @return
	 */
	private int calculateTranslation() {
		if (isHeader) {
			return calculateTranslationXY(!isReversed);
		} else {
			return calculateTranslationXY(isReversed);
		}
	}

	/**
	 * 计算距离的方法
	 * @param isTop
	 * @return
	 */
	private int calculateTranslationXY(boolean isTop) {
		if (!isTop) {
			int offset = getScrollOffset();
			int base = getScrollRange() - getSize();
			return base - offset;
		} else {
			return -getScrollOffset();
		}
	}


	private boolean hasItems() {
		return recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() != 0;
	}


	private int getScrollOffset() {
		return isVertical ? recyclerView.computeVerticalScrollOffset() : recyclerView.computeHorizontalScrollOffset();
	}

	private int getSize() {
		return isVertical ? getMeasuredHeight() : getMeasuredWidth();
	}


	private int getScrollRange() {
		return isVertical ? recyclerView.computeVerticalScrollRange() : recyclerView.computeHorizontalScrollRange();
	}


	/**
	 * 第一项是否显示
	 * @return
	 */
	private boolean isFirstRowVisible() {
		if (layoutManager instanceof GridLayoutManager) {
			GridLayoutManager grid = (GridLayoutManager) layoutManager;
			return grid.findFirstVisibleItemPosition() == 0;
		} else if (layoutManager instanceof LinearLayoutManager) {
			LinearLayoutManager linear = (LinearLayoutManager) layoutManager;
			return linear.findFirstVisibleItemPosition() == 0;
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager staggeredGrid = (StaggeredGridLayoutManager) layoutManager;
			int[] positions = staggeredGrid.findFirstVisibleItemPositions(null);
			Arrays.sort(positions);
			return positions[0] == 0;
		}
		return false;
	}


	/**
	 * 最后一项是否显示
	 */
	private boolean isLastRowVisible() {
		if (layoutManager instanceof GridLayoutManager) {
			GridLayoutManager grid = (GridLayoutManager) layoutManager;
			return grid.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1;
		} else if (layoutManager instanceof LinearLayoutManager) {
			LinearLayoutManager linear = (LinearLayoutManager) layoutManager;
			return linear.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1;
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager staggeredGrid = (StaggeredGridLayoutManager) layoutManager;
			int[] positions = staggeredGrid.findLastVisibleItemPositions(null);
			Arrays.sort(positions);
			return positions[staggeredGrid.getSpanCount() - 1] >= layoutManager.getItemCount() - 1;
		}
		return false;
	}


	public interface OnLoadMoreListener {

		void onLoadMore();
	}

	public interface OnOffsetChangedListener {
		void onOffsetChanged(PlusRecyclerHeaderFooter view, int offset);
	}
}
