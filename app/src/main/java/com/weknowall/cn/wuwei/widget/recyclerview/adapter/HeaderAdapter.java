package com.weknowall.cn.wuwei.widget.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.weknowall.cn.wuwei.utils.Logs;

import java.util.List;

/**
 * **********************
 * Author: yu
 * Date:   2015/6/16
 * Time:   13:35
 * 可以添加Header和Footer的Adapter
 * **********************
 */
public abstract class HeaderAdapter<T> extends AdapterPlus<T> {
	private static final String TAG = "HeaderAndFooterAdapter";

	private static final int ITEM_TYPE_HEADER = -0x1;
	private static final int ITEM_TYPE_FOOTER = -0x2;

	public HeaderAdapter(Context context) {
		super(context);
	}

	public HeaderAdapter(Context context, List<T> list) {
		super(context, list);
	}

	@Override
	public ViewHolderPlus<T> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
		switch (viewType) {
			case ITEM_TYPE_HEADER:
				return onCreateHeaderHolder(parent, inflater);
			case ITEM_TYPE_FOOTER:
				return onCreateFooterHolder(parent, inflater);
			default:
				return onCreateItemViewHolder(parent, viewType, inflater);
		}
	}

	public abstract ViewHolderPlus<T> onCreateItemViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater);

	public ViewHolderPlus<T> onCreateHeaderHolder(ViewGroup parent, LayoutInflater inflater) {
		return null;
	}

	public ViewHolderPlus<T> onCreateFooterHolder(ViewGroup parent, LayoutInflater inflater) {
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolderPlus<T> holder, int position) {
		if (isHasHeader() && position == 0) {
			onBindHeaderViewHolder(holder);
		} else if (isHasFooter() && position == getItemCount() - 1) {
			onBindFooterViewHolder(holder);
		} else {
			super.onBindViewHolder(holder, getRealItemPosition(position));
			onBindItemViewHolder(holder, getRealItemPosition(position));
		}
	}

	public void onBindItemViewHolder(ViewHolderPlus<T> holder, int position) {

	}

	public void onBindHeaderViewHolder(ViewHolderPlus<T> headerHolder) {
		if (headerHolder != null) {
			//			headerHolder.setIsRecyclable(false);
			headerHolder.onBinding(0, null);
		}
	}

	public void onBindFooterViewHolder(ViewHolderPlus<T> footerHolder) {
		if (footerHolder != null) {
			footerHolder.onBinding(getRealItemCount(), null);
		}
	}

	public int getRealItemPosition(int position) {
		return isHasHeader() ? position - 1 : position;
	}

	@Override
	public int getItemCount() {
		if (getList() != null) {
			return getList().size() + (isHasHeader() ? 1 : 0) + (isHasFooter() ? 1 : 0);
		}
		return super.getItemCount();
	}

	public int getRealItemCount() {
		return getList().size();
	}

	public boolean isHasHeader() {
		return false;
	}

	public boolean isHasFooter() {
		return false;
	}

	@Override
	public int getItemViewType(int position) {
		if (isHasHeader() && position == 0)
			return ITEM_TYPE_HEADER;
		else if (isHasFooter() && position == getItemCount() - 1)
			return ITEM_TYPE_FOOTER;
		return getRealItemViewType(getRealItemPosition(position));
	}

	/**
	 * 获取非Header&Footer子项ViewType
	 * @param position 真实的position
	 * @return 返回该子项的ViewType
	 */
	public int getRealItemViewType(int position) {
		return 0;
	}


	@Override
	public void change(T t) {
		int index = getList().indexOf(t);
		change(isHasHeader() ? index + 1 : index);
	}

	@Override
	public void change(int adapterPosition) {
		super.change(isHasHeader() ? adapterPosition + 1 : adapterPosition);
	}

	@Override
	public void delete(T t) {
		int index = getList().indexOf(t);
		delete(isHasHeader() ? index + 1 : index);
	}

	@Override
	public void delete(int adapterPosition) {
		int dataPosition = getDataPosition(adapterPosition);
		getList().remove(dataPosition);
		notifyItemRemoved(adapterPosition);
	}

	/**
	 * 删除
	 * @param adapterPosition 需要插入的item position,该position为在adapter布局中的绝对位置,即如果有Header时,此时的position较普通
	 *                        的position +1;
	 * @param itemCount       需要删除的数量
	 */
	@Override
	public void delete(int adapterPosition, int itemCount) {
		if (isHasHeader() && adapterPosition == 0)
			throw new IndexOutOfBoundsException("adapter has header and adapterPosition must > 0");
		int dataPosition = getDataPosition(adapterPosition);
		List<T> li = getList().subList(dataPosition, dataPosition + itemCount);
		if (li.size() == 0)
			return;
		getList().removeAll(li);
		notifyItemRangeRemoved(adapterPosition, itemCount);
	}

	@Override
	public void insert(T t, boolean isScroll) {
		insert(isHasHeader() ? getRealItemCount() + 1 : getRealItemCount(), t, isScroll);
	}

	@Override
	public void insertRange(List<T> list) {
		insertRange(list, true);
	}

	@Override
	public void insertRange(List<T> list, boolean anim) {
		insertRange(isHasHeader() ? getRealItemCount() + 1 : getRealItemCount(), list, anim);
	}


	/**
	 * 新增
	 * @param adapterPosition 需要插入的item position,该position为在adapter布局中的绝对位置,即如果有Header时,此时的position较普通
	 *                        的position +1;
	 * @param ts              需要添加的数据列表
	 * @param anim            添加数据时,是否滚动到新数据位置
	 */
	@Override
	public void insertRange(int adapterPosition, List<T> ts, boolean anim) {
		if (isHasHeader() && adapterPosition == 0)
			throw new IndexOutOfBoundsException("adapter has header and adapterPosition must > 0");
		int dataPosition = getDataPosition(adapterPosition);

		if (ts == null)
			return;
		if (getList().addAll(dataPosition, ts)) {
			if (anim) {
				notifyItemInserted(adapterPosition);
			} else {
				notifyDataSetChanged();
			}
		}
	}

	@Override
	public void insert(int adapterPosition, T t, boolean isScroll) {
		if (isHasHeader() && adapterPosition == 0)
			throw new IndexOutOfBoundsException("adapter has header and adapterPosition must > 0");
		int dataPosition = getDataPosition(adapterPosition);
		getList().add(dataPosition, t);
		if (isScroll) {
			Logs.e(TAG, "insert isScroll position is " + adapterPosition);
			notifyItemInserted(adapterPosition);
		} else {
			notifyDataSetChanged();
		}
	}

	int getDataPosition(int adapterPosition) {
		return isHasHeader() ? adapterPosition - 1 : adapterPosition;
	}

	public void changeHeader() {
		if (isHasHeader() && getItemCount() > 0)
			super.change(0);
	}

	public void changeFooter() {
		if (isHasFooter() && getItemCount() > 0)
			super.change(getItemCount() - 1);
	}
}
