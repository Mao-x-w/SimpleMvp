package com.weknowall.app_presenter.view;

import android.support.annotation.StringRes;

/**
 * User: laomao
 * Date: 2017-01-19
 * Time: 10-09
 */

public interface ILoadingView extends IResultView{
    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * 显示当前内容
     * @param message
     */
    void showMessage(String message);

    /**
     * 显示当前内容
     * @param resId 内容的ResourceId{@link android.support.annotation.StringRes}
     */
    void showMessage(@StringRes int resId);
}
