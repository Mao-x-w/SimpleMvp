package com.weknowall.cn.wuwei.widget.recyclerview.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weknowall.cn.wuwei.utils.Logs;

import java.util.ArrayList;
import java.util.List;


/**
 * **********************
 * Author: yu
 * Date:   2015/6/16
 * Time:   14:12
 * **********************
 */
public abstract class AdapterPlus<T> extends RecyclerView.Adapter<ViewHolderPlus<T>> {

	private static final String TAG = "AdapterPlus";
	private Context mContext;
	private List<T> mList;

	public AdapterPlus(Context context) {
		this(context, (List<T>) new ArrayList<>());
	}

	public AdapterPlus(Context context, List<T> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public ViewHolderPlus<T> onCreateViewHolder(ViewGroup parent, int viewType) {
		return onCreateViewHolder(parent, viewType, LayoutInflater.from(getContext()));
	}

	public abstract ViewHolderPlus<T> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater);

	@Override
	public void onBindViewHolder(ViewHolderPlus<T> holder, int position) {
		if (mList != null && mList.size() > 0 ) {
			holder.setItemObject(mList.get(position));
			holder.onBinding(position, mList.get(position));
		} else {
			holder.onBinding(position, null);
		}

	}

	public Context getContext() {
		return mContext;
	}

	public List<T> getList() {
		return mList;
	}

	@Override
	public int getItemCount() {
		return getList().size();
	}

	protected View createView(int layoutId, ViewGroup parent, LayoutInflater inflater) {
		return inflater.inflate(layoutId, parent, false);
	}

	protected View createView(@LayoutRes int layoutId, ViewGroup parent) {
		return createView(layoutId, parent, LayoutInflater.from(getContext()));
	}

	public void insert(int adapterPosition, T t, boolean anim) {
		Logs.e(TAG, "insert position " + adapterPosition);
		/** 如果不可重复的 */
		if (!isMemberRepeatability() && mList.contains(t))
			return;
		mList.add(adapterPosition, t);
		if (anim)
			notifyItemInserted(adapterPosition);
		else
			notifyDataSetChanged();
	}

	public void insert(int adapterPosition, T t) {
		insert(adapterPosition, t, true);
	}

	public void insert(T t, boolean isScroll) {
		insert(getItemCount(), t, isScroll);
	}

	public void insert(T t) {
		insert(t, true);
	}

	/**
	 * @param adapterPosition Position of the first item that was inserted
	 * @param list            list to insert
	 * @param anim            是否展示动画
	 */
	public void insertRange(int adapterPosition, List<T> list, boolean anim) {
		if (list == null)
			return;
		if (mList.addAll(adapterPosition, list))
			if (anim) {
				notifyItemRangeInserted(adapterPosition, list.size());
			} else {
				notifyDataSetChanged();
			}
	}

	public void insertRange(List<T> list, boolean anim) {
		insertRange(getList().size(), list, anim);
	}

	/**
	 * 添加
	 * @param adapterPosition 需要插入的item position,该position为在adapter布局中的绝对位置,即如果有Header时,此时的position较普通
	 *                        的position +1;
	 */
	public void insertRange(int adapterPosition, List<T> list) {
		insertRange(adapterPosition, list, true);
	}

	public void insertRange(List<T> list) {
		insertRange(0, list);
	}

	public void delete(T t) {
		int position = mList.indexOf(t);
		if (position >= 0) {
			delete(position);
		}
	}

	/**
	 * 删除
	 * @param adapterPosition 需要插入的item position,该position为在adapter布局中的绝对位置,即如果有Header时,此时的position较普通
	 *                        的position +1;
	 */
	public void delete(int adapterPosition) {
		if (adapterPosition >= mList.size())
			return;
		mList.remove(adapterPosition);
		notifyItemRemoved(adapterPosition);
	}

	public void delete(int adapterPosition, int itemCount) {
		if (adapterPosition >= mList.size() || adapterPosition + itemCount > mList.size())
			return;
		List<T> li = mList.subList(adapterPosition, adapterPosition + itemCount);
		if (li.size() == 0)
			return;
		mList.removeAll(li);
		notifyItemRangeRemoved(adapterPosition, itemCount);
	}

	public void deleteRange(List<T> list) {
		int index = list.indexOf(list.get(0));
		if (index < 0)
			return;
		if (mList.removeAll(list)) {
			notifyItemRangeRemoved(index, list.size());
		}
	}

	public void change(T t, boolean replace) {
		if (replace) {
			mList.set(mList.indexOf(t), t);
		}
		change(mList.indexOf(t));
	}

	public void change(T t) {
		change(t, true);
	}

	/**
	 * 更新
	 * @param adapterPosition 需要插入的item position,该position为在adapter布局中的绝对位置,即如果有Header时,此时的position较普通
	 *                        的position +1;
	 */
	public void change(int adapterPosition) {
		notifyItemChanged(adapterPosition);
	}

	/**
	 * @param startAdapterPosition 初始adapterPosition
	 * @param itemCount            需要change的数量
	 */
	public void change(int startAdapterPosition, int itemCount) {
		notifyItemRangeChanged(startAdapterPosition, itemCount);
	}

	public synchronized void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	/**
	 * list中成员变量的重复性
	 * @return true 可重复 false 不可重复
	 */
	public boolean isMemberRepeatability() {
		return true;
	}
}
