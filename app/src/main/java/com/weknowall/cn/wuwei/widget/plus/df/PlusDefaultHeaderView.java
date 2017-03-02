package com.weknowall.cn.wuwei.widget.plus.df;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canrefresh.CanRefresh;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.utils.SharePreferencePlus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: JiYu
 * Date: 2016-09-21
 * Time: 10-44
 */

class PlusDefaultHeaderView extends FrameLayout implements CanRefresh {

	private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	private ImageView mIcon;
	TextView mTextLastTime, mTextHint;

	//  提示文字组合
	private String[] mHintStrings;

	private LastTimeSharedPreferences mLastTimeSaved;

	private Long mLastUpdateTime = -1L;

	public PlusDefaultHeaderView(Context context) {
		this(context, null);
	}

	public PlusDefaultHeaderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlusDefaultHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.plus_frame_headerview_default, this, true);
		mIcon = (ImageView) findViewById(R.id.ptr_header_image);
		mTextLastTime = (TextView) findViewById(R.id.ptr_header_time);
		mTextHint = (TextView) findViewById(R.id.ptr_header_hint);
		mHintStrings = getResources().getStringArray(R.array.ptr_recycler_refresh_hint);
		mLastTimeSaved = new LastTimeSharedPreferences(getContext());
		mLastTimeSaved.setKey(getContext().getClass().getName());
	}

	/**
	 * 设置用于保存上次刷新时间的key
	 * @param key 上次刷新时间的key
	 */
	public void setLastUpdateTimeKey(String key) {
		mLastTimeSaved.setKey(key);
	}

	@Override
	public void onReset() {
	}

	@Override
	public void onPrepare() {
		mTextHint.setText(mHintStrings[0]);
		tryUpdateLastTime();
	}

	@Override
	public void onRelease() {
		mTextHint.setText(mHintStrings[2]);
	}

	@Override
	public void onComplete() {
		mLastTimeSaved.setTime(mLastUpdateTime = System.currentTimeMillis());
		mTextHint.setText(mHintStrings[3]);
	}

	@Override
	public void onPositionChange(float currentPercent) {
		if (currentPercent < 1) {
			mTextHint.setText(mHintStrings[0]);
		} else {
			mTextHint.setText(mHintStrings[1]);
		}
	}

	@Override
	public void setIsHeaderOrFooter(boolean isHeader) {

	}

	/**
	 * 更新上次加载时间
	 */
	private void tryUpdateLastTime() {
		if (mLastUpdateTime == -1) {
			mLastUpdateTime = mLastTimeSaved.getTime();
		}
		if (mLastUpdateTime == -1) {
			return;
		}
		long diffTime = System.currentTimeMillis() - mLastUpdateTime;
		int seconds = (int) (diffTime / 1000);
		if (diffTime < 0) {
			return;
		}
		if (seconds <= 0) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getContext().getString(R.string.cube_ptr_last_update));
		if (seconds < 60) {
			sb.append(seconds).append(getContext().getString(R.string.cube_ptr_seconds_ago));
		} else {
			int minutes = (seconds / 60);
			if (minutes > 60) {
				int hours = minutes / 60;
				if (hours > 24) {
					Date date = new Date(mLastUpdateTime);
					sb.append(sDataFormat.format(date));
				} else {
					sb.append(hours).append(getContext().getString(R.string.cube_ptr_hours_ago));
				}

			} else {
				sb.append(minutes).append(getContext().getString(R.string.cube_ptr_minutes_ago));
			}
		}
		mTextLastTime.setVisibility(VISIBLE);
		mTextLastTime.setText(sb.toString());
	}

	class LastTimeSharedPreferences extends SharePreferencePlus {

		private static final String NAME = "ptr_header_msj";
		private String key;

		LastTimeSharedPreferences(Context context) {
			super(context, NAME);
		}

		public Long getTime() {
			return getLongValue(key, -1);
		}

		public void setTime(Long time) {
			editLongValue(key, time);
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
}
