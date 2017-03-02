package com.weknowall.app_presenter.presenter;

import android.support.annotation.NonNull;

import com.weknowall.app_domain.entity.Listable;
import com.weknowall.app_domain.interactor.UseCase;
import com.weknowall.app_presenter.view.ILoadingPageListCommonView;
import com.weknowall.app_presenter.view.ILoadingView;
import com.weknowall.app_presenter.view.IPageList;

import java.util.List;

/**
 * User: laomao
 * Date: 2017-01-20
 * Time: 11-24
 */

public abstract class LoadingPageListCommonPresenter<RQM extends Listable,RPM extends List<?>, RP, V extends ILoadingPageListCommonView<RP>> extends LoadingPresenter<RQM, RQM, RPM, RP, V> implements IPageListPresenter {

    private IPageList mPageList;

    private boolean canShowLoading;
    public RQM mListable;

    public LoadingPageListCommonPresenter(@NonNull UseCase<RQM, RPM> useCase) {
        super(useCase);
    }

    @Override
    public void initialize(RQM... rqms) {
        mListable = rqms[0];
        execute(rqms);
        onPageListLoading(mListable.getLoadType());

        if (isLoadingMore()){
            // 还可以加载更多
            mListable.setPage(mListable.getPage()+mListable.PAGE_SIZE_DEFAULT);
            mListable.setRealPage(mListable.getRealPage()+mListable.PAGE_SIZE_DEFAULT);
        }
    }

    @Override
    public void onNext(RPM rpm) {
        super.onNext(rpm);
        if (rpm==null||rpm.size()==0){
            if (isLoadingMore()){
                onPageListStateChanged(IPageList.STATE_NOMORE);
            }else {
                onPageListStateChanged(IPageList.STATE_EMPTY);
            }
        }else {
            onPageListStateChanged(IPageList.STATE_LOADING);
        }

    }

    @Override
    public void setPageList(IPageList pageList) {
        mPageList = pageList;
    }

    @Override
    public void setCanShowLoading(boolean canShowLoading) {
        this.canShowLoading = canShowLoading;
    }

    @Override
    public boolean canShowLoading() {
        return canShowLoading;
    }

    /**
     * 是否为初次加载数据？
     */
    protected boolean isInitialLoading() {
        return mListable != null && mListable.isFirstInitial();
    }

    /**
     * 是否是加载更多
     * @return
     */
    protected boolean isLoadingMore(){
        return mListable.isLoadingMore();
    }

    /**
     * 设置是否可以显示加载动画，如果为true的话表示可以调用ILoadingView中的加载动画，
     * 否则调用IPageListView中的加载动画
     * 当{@link #canShowLoading()} 为true时，并且当前为初次加载{@link #isInitialLoading()}的时候，
     * 才会调用当前的ILoadingView{@link #getView()}去显示加载框{@link ILoadingView#showLoading()}
     */
    @Override
    public void showLoading() {
        if (canShowLoading && isInitialLoading())
            super.showLoading();
    }

    @Override
    public void onError() {
        super.onError();
        onPageListStateChanged(IPageList.STATE_ERROR);
    }

    protected void onPageListLoading(int loadType) {
        if (mPageList != null)
            mPageList.onLoading(loadType);
    }

    protected void onPageListStateChanged(int state) {
        if (mPageList != null)
            mPageList.onPageStateChanged(state);
    }


}
