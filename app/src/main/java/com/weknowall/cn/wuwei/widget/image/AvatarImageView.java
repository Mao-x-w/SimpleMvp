package com.weknowall.cn.wuwei.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.weknowall.cn.wuwei.R;


/**
 * User: Joy
 * Date: 2016/12/5
 * Time: 10:31
 */

public class AvatarImageView extends CircleImageView {

	private boolean showV = false;
	private Drawable mDrawableVip;

	public AvatarImageView(Context context) {
		this(context, null);
	}

	public AvatarImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDrawableVip = context.getResources().getDrawable(R.drawable.draw_vector_user_big_vip);
	}

	public void setShowVip(boolean showV) {
		this.showV = showV;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showV) {
			mDrawableVip.setBounds(getWidth() - (int)(getWidth() / 2.5f), getHeight() - (int)(getWidth() / 2.5f), getWidth(), getHeight());
			mDrawableVip.draw(canvas);
		}
	}
}
