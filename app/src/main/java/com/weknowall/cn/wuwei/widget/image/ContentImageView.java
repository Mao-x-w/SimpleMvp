package com.weknowall.cn.wuwei.widget.image;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.cn.wuwei.utils.image.ImageLoader;


/**
 * User: JiYu
 * Date: 2016-09-22
 * Time: 10-38
 */

public class ContentImageView extends WebImageView {

	public ContentImageView(Context context) {
		super(context);
	}

	public ContentImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setImageUrl(String url) {
		super.setImageUrl(url);
		ImageLoader.display(getContext(), "http://" + url, this, ImageLoader.defaultOptions()
				.size(DeviceHelper.getScreenWidth(), DeviceHelper.getScreenWidth())
				.transform(new FitCenter(getContext())));
	}

	@Override
	protected void onDisplayImage(String url) {
	}
}
