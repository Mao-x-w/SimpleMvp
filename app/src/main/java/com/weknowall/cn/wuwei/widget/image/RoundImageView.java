package com.weknowall.cn.wuwei.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.weknowall.cn.wuwei.utils.image.ImageLoader;

/**
 * User: JiYu
 * Date: 2016-09-22
 * Time: 16-44
 */

public class RoundImageView extends WebImageView {

	public enum Radius {
		Small(com.weknowall.cn.wuwei.R.drawable.image_loading_round_2, com.weknowall.cn.wuwei.R.dimen.size_2),
		Normal(com.weknowall.cn.wuwei.R.drawable.image_loading_round_5, com.weknowall.cn.wuwei.R.dimen.size_5),
		Large(com.weknowall.cn.wuwei.R.drawable.image_loading_round_10, com.weknowall.cn.wuwei.R.dimen.size_10);

		private int loadingRes;
		private int radiusRes;

		Radius(int loadingRes, int radiusRes) {
			this.radiusRes = radiusRes;
			this.loadingRes = loadingRes;
		}

		public int getLoadingRes() {
			return loadingRes;
		}

		public int getRadiusRes() {
			return radiusRes;
		}
	}

	private Radius radius = Radius.Normal;

	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs, com.weknowall.cn.wuwei.R.styleable.RoundImageView);
		int index = ta.getInt(com.weknowall.cn.wuwei.R.styleable.RoundImageView_ri_radius, -1);
		Radius radius = Radius.Normal;
		if (index >= 0) {
			switch (index) {
				case 0:
					radius = Radius.Small;
					break;
				case 1:
					radius = Radius.Normal;
					break;
				case 2:
					radius = Radius.Large;
					break;
			}
		}
		ta.recycle();
		if (radius != null) {
			setRadius(radius);
		}
	}

	@Override
	public int getImageLoadingResource() {
		return super.getImageLoadingResource();
	}

	public void setRadius(Radius radius) {
		this.radius = radius;
		setBackgroundResource(radius.getLoadingRes());
	}

	@Override
	protected void onDisplayImage(String url) {
		if (!TextUtils.isEmpty(url))
			ImageLoader.displayRound(getContext(), url, this, getResources().getDimensionPixelOffset(radius.getRadiusRes()), ImageLoader.defaultOptions()
//					.centerCrop()
					.loading(radius.getLoadingRes())
					.error(radius.getLoadingRes()));
	}
}
