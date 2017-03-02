package com.weknowall.cn.wuwei.utils;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;


import com.weknowall.cn.wuwei.R;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * User: JiYu
 * Date: 2016-09-01
 * Time: 18-30
 */

public class UiHelper {

	private Activity context;
	private AlertDialog mLoadingDialog;
	private View mLoadingView;

	public UiHelper(Activity context) {
		this.context = context;
	}

	/**
	 * 弹吐司 mainThread
	 * @param text 吐司文本
	 */
	public void showToast(String text) {
		Observable.just(text).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> Toast.makeText(context, s, Toast.LENGTH_SHORT).show());
	}

	/**
	 * 显示弹出框
	 */
	public synchronized void showLoadingDialog() {
		if (mLoadingView != null) {
			mLoadingView.setVisibility(View.VISIBLE);
		}
		Observable.just(getLoadingDialog()).observeOn(AndroidSchedulers.mainThread()).subscribe(Dialog::show, Throwable::printStackTrace);
	}

	public synchronized void dismissLoadingDialog() {
		if (mLoadingView != null) {
			mLoadingView.setVisibility(View.GONE);
		}
		Observable.just(getLoadingDialog()).observeOn(AndroidSchedulers.mainThread()).subscribe(Dialog::dismiss, Throwable::printStackTrace);
	}

	private synchronized AlertDialog getLoadingDialog() {
		synchronized (AlertDialog.class) {
			if (mLoadingDialog == null) {
				synchronized (AlertDialog.class) {
					mLoadingDialog = new AlertDialog.Builder(context, R.style.LaoMao_Widget_Dialog_Transparent) //
							.setView(View.inflate(context, R.layout.dialog_loading_old, null)) //
							.create(); //
					mLoadingDialog.setCanceledOnTouchOutside(false);
					mLoadingView = mLoadingDialog.findViewById(R.id.loading_view);
				}
			}
		}
		return mLoadingDialog;
	}
}
