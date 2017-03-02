package com.weknowall.cn.wuwei.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;


/**
 * User: Joy
 * Date: 2016/12/13
 * Time: 17:11
 */

public class LoadingDialogView extends FrameLayout {

	ImageView mImageLoading;
	AnimationDrawable mAnimDrawable;

	public LoadingDialogView(Context context) {
		this(context, null);
	}

	public LoadingDialogView(Context context, AttributeSet attr){
		super(context, attr);
		mImageLoading = new ImageView(getContext());
		addView(mImageLoading, -2, -2);
		mAnimDrawable = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.draw_anim_loading);
		mImageLoading.setImageDrawable(mAnimDrawable);
	}

	public void start() {
		if (mAnimDrawable != null)
			mAnimDrawable.start();
	}

	public void stop() {
		if (mAnimDrawable != null)
			mAnimDrawable.stop();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		start();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stop();
	}
}
