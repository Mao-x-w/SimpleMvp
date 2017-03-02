package com.weknowall.app_presenter.view;

import com.weknowall.app_domain.entity.Listable;

/**
 * User: laomao
 * Date: 2017-01-19
 * Time: 12-24
 */

public interface IPageList {

    /**
     * 列表的加载类型
     */
    int LOADING_TYPE_GET= Listable.LoadType.GET;
    int LOADING_TYPE_MORE= Listable.LoadType.MORE;
    int LOADING_TYPE_REFRESH= Listable.LoadType.REFRESH;

    int STATE_NONE=1;
    int STATE_EMPTY=2;
    int STATE_LOADING=3;
    int STATE_NOMORE=4;
    int STATE_ERROR=5;

    /**
     * 加载状态改变时（通过网络回来的数据进行判断）
     * @param state
     */
    void onPageStateChanged(int state);

    /**
     * 开始加载时 （可分为普通加载、下拉刷新、上拉加载）
     * @param loadType
     */
    void onLoading(int loadType);

}
