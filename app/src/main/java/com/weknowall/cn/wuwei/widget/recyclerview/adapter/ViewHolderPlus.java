package com.weknowall.cn.wuwei.widget.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * **********************
 * Author: yu
 * Date:   2015/6/18
 * Time:   10:44
 * **********************
 */
public abstract class ViewHolderPlus<T> extends RecyclerView.ViewHolder {

    private T object;
	private Context context;

    public ViewHolderPlus(View itemView) {
        super(itemView);
		context = itemView.getContext();
    }
    /**
     * 获取rootView
     * @return itemView
     */
    public View getItemView() {
        return itemView;
    }

    /**
     * 绑定到RecycleViewPlus时触发
     * @param position 当前ViewHolder的position
     * @param t 当前list中对应位置的对象
     */
    public abstract void onBinding(int position, final T t);

    public void setItemObject(T object){
        this.object = object;
    }

	/**
	 * 获取item的Object
	 * @return t
	 */
	public T getItemObject() {
		return object;
	}

    /**
     * itemView 点击事件
     * @param onItemViewClick onItemViewClick
     */
    public void setOnItemViewClick(View.OnClickListener onItemViewClick) {
        if (getItemView() != null) {
            getItemView().setOnClickListener(onItemViewClick);
        }
    }

    /**
     * 用于实现一些想要在adpater中进行停止的操作
     * 空实现
     */
    public void stop(){}

    /**
     * 用于实现一些想要在adpater中进行开始的操作
     * 空实现
     */
    public void start(){}

	public Context getContext() {
		return context;
	}
}
