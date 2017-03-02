package com.weknowall.cn.wuwei.widget.image;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.weknowall.cn.wuwei.utils.image.ImageLoader;


/**
 * User: JiYu
 * Date: 2016-09-22
 * Time: 09-42
 * 头像图片
 */

public class CircleImageView extends WebImageView {

	public CircleImageView(Context context) {
		this(context, null);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setImageUrl(String url) {
		super.setImageUrl(url);
		onDisplayImage(url);
	}

	@Override
	public int getImageLoadingResource() {
		return com.weknowall.cn.wuwei.R.drawable.image_loading_circle;
	}

	@Override
	protected void onDisplayImage(String url) {
		if (TextUtils.isEmpty(url))
			return;
		if (getImageLoadingResource() == NO_ID) {
			ImageLoader.displayCircle(getContext(), url, this);
		} else {
			ImageLoader.displayCircle(getContext(), url, this, ImageLoader.defaultOptions()
					.error(getImageLoadingResource())
					.loading(getImageLoadingResource()));
		}
	}
}
