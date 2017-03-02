package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.weknowall.cn.wuwei.R;

import java.util.ArrayList;
import java.util.List;


public abstract class TabViewBase<T extends View> extends LinearLayout implements ITabViewGroup<T> {

	private OnSelectorChangedListener<T> mySelecterChangedListener;
	private OnSelectorChangedListener2<T> mySelecterChangedListener2;
	private int selectId = 0;
	private List<T> mViewList;
	public Drawable selectedDraw, unSelectedDraw;
	public int selectedTextColor = 0, unSelectedTextColor = 0;

	public TabViewBase(Context context) {
		this(context, null);
	}

	public TabViewBase(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TabViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mViewList = new ArrayList<>();
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabViewGroup);
		if (ta.hasValue(R.styleable.TabViewGroup_SelectedBackground)) {
			selectedDraw = ta.getDrawable(R.styleable.TabViewGroup_SelectedBackground);
		}
		if (ta.hasValue(R.styleable.TabViewGroup_UnSelectedBackground)) {
			unSelectedDraw = ta.getDrawable(R.styleable.TabViewGroup_UnSelectedBackground);
		}
		if (ta.hasValue(R.styleable.TabViewGroup_SelectedTextColor)) {
			selectedTextColor = ta.getInteger(R.styleable.TabViewGroup_SelectedTextColor, getResources().getColor(android.R.color.black));
		}
		if (ta.hasValue(R.styleable.TabViewGroup_UnSelectTextColor)) {
			unSelectedTextColor = ta.getInteger(R.styleable.TabViewGroup_UnSelectTextColor, getResources().getColor(android.R.color.black));
		}
		ta.recycle();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			for (int i = 0; i < getChildCount(); i++) {
				View view = getChildAt(i);
				if (isInstanceOfChild(view)) {
					addCustomChildView((T) view);
				}
			}
			setSelected(0);
		}
	}

	/**
	 * if inflate time has child instanceof my custom view, add it to my ChildList
	 */
	public abstract boolean isInstanceOfChild(View v);

	@Override
	public void setOnSelectorChangedListener(OnSelectorChangedListener<T> mListener) {
		this.mySelecterChangedListener = mListener;
	}

	@Override
	public void setOnSelectorChangedListener(OnSelectorChangedListener2<T> mListener) {
		this.mySelecterChangedListener2 = mListener;
	}

	@Override
	public boolean setSelected(int index) {
		return setSelected(index, false);
	}

	@Override
	public boolean setSelected(int index, boolean isFromUser) {
		boolean isSuccess = false;
		if (index < 0) {
			for (int i = 0; i < getCustomChildViewList().size(); i++) {
				getCustomChildViewList().get(i).setSelected(false);
			}
			selectId = -1;
			return true;
		}
		if (getCustomChildViewList().size() > index) {
			selectId = index;
			updateChildStyle(index);
			for (int i = 0; i < getCustomChildViewList().size(); i++) {
				getCustomChildViewList().get(i).setSelected(i == index);
			}
			if (mySelecterChangedListener != null)
				mySelecterChangedListener.OnSelectorChanged(index, getCustomChildViewList().get(index));
			if (mySelecterChangedListener2 != null)
				mySelecterChangedListener2.OnSelectorChanged(index, getCustomChildViewList().get(index), isFromUser);
			isSuccess = true;
		}
		return isSuccess;
	}

	/**
	 * update childs style( background、textColor... );
	 */
	public abstract void updateChildStyle(int currentSelectId);

	@Override
	public int getSelected() {
		return selectId;
	}

	@Override
	public int getCustomChildCount() {
		return mViewList.size();
	}

	public List<T> moveLeft() {
		return mViewList;
	}

	public void setCustomChildViewList(List<T> mViewList) {
		this.mViewList = mViewList;
	}

	public void addCustomChildView(T t) {
		mViewList.add(t);
		updateChildViewClickListener();
	}

	@Override
	public void removeAllViews() {
		super.removeAllViews();
		mViewList.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addView(View child) {
		super.addView(child);
		if(isInstanceOfChild(child))
		addCustomChildView((T) child);
	}

	public void addCustomChildViews(List<T> tList) {
		mViewList.addAll(tList);
		for (int i = 0; i < tList.size(); i++) {
			addView(tList.get(i));
		}
		updateChildViewClickListener();
	}


	private void updateChildViewClickListener() {
		for (int i = 0; i < mViewList.size(); i++) {
			final int index = i;
			mViewList.get(index).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setSelected(index, true);
				}
			});
		}
	}

	public OnSelectorChangedListener<T> getOnSelectorChangedListener() {
		return mySelecterChangedListener;
	}

	@Override
	public List<T> getCustomChildViewList() {
		return mViewList;
	}

}
