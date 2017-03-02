package com.weknowall.app_presenter.view;

/**
 * User: laomao
 * Date: 2017-01-23
 * Time: 10-53
 */

public interface ILoadingPageListCommonView<IL> extends ILoadingView {

    void onGet(IL itemList, Object... params);

    void onMore(IL itemList, Object... params);
}
