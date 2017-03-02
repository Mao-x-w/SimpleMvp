package com.weknowall.app_presenter.view;

import java.util.List;

/**
 * User: laomao
 * Date: 2017-01-23
 * Time: 10-53
 */

public interface ILoadingPageListView<IL> extends ILoadingView {

    void onGet(List<IL> itemList,Object... params);

    void onMore(List<IL> itemList,Object... params);
}
