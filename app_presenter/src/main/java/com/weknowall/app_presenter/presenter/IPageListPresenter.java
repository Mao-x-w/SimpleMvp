package com.weknowall.app_presenter.presenter;

import com.weknowall.app_presenter.view.IPageList;

/**
 * User: laomao
 * Date: 2017-01-19
 * Time: 17-00
 */

public interface IPageListPresenter {

    void setPageList(IPageList pageList);

    /**
     * 设置是否可以显示页面(? extends ILoadingView)中的加载动画，如果为true的话表示可以调用ILoadingView中的加载动画，
     * 否则调用IPageListView中的加载动画
     */
    boolean canShowLoading();
}
