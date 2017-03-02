package com.weknowall.cn.wuwei.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.utils.image.ImageLoader;


/**
 * User: JiYu
 * Date: 2016-09-06
 * Time: 15-51
 */

public class WebImageView extends ImageView {

	private String url = "";
	private int width;
	private int height;
	private boolean displayed = false;
	private int loadingResId = R.drawable.image_loading;

	public WebImageView(Context context) {
		this(context, null);
	}

	public WebImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WebImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WebImageView);
		loadingResId = ta.getResourceId(R.styleable.WebImageView_wi_loading, R.drawable.image_loading);
		ta.recycle();
		setImageLoadingResource(loadingResId);
		setImageResource(getImageLoadingResource());
		// 設置夜間模式
		if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
			setAlpha(0.8f);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed && !displayed && (right > left || bottom > top)) {
			onDisplayImage(getImageUrl());
			displayed = true;
		}
	}

	public void setImageLoadingResource(@DrawableRes int resId) {
		this.loadingResId = resId;
	}

	public int getImageLoadingResource() {
		return loadingResId;
	}

	public void setImageUrl(String url) {
		this.url = url;
		if (getWidth() != 0 && getHeight() != 0)
			onDisplayImage(url);
	}

	public void setImageUrl(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
		onDisplayImage(url);
	}

	protected void onDisplayImage(String url) {
		try {
			ImageLoader.Options options = ImageLoader.defaultOptions().loading(loadingResId).error(loadingResId);
			if (width != 0 && height != 0) {
				options.size(width, height);
			}
			ImageLoader.display(getContext(), url, this, options.centerCrop());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getImageUrl() {
		return url;
	}
}
