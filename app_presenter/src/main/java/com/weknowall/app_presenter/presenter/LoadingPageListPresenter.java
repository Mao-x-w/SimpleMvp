package com.weknowall.app_presenter.presenter;

import android.support.annotation.NonNull;

import com.weknowall.app_common.utils.MapperImpl;
import com.weknowall.app_domain.entity.Listable;
import com.weknowall.app_domain.entity.PageListModel;
import com.weknowall.app_domain.interactor.UseCase;
import com.weknowall.app_presenter.entity.PageList;
import com.weknowall.app_presenter.mapper.PageListMapper;
import com.weknowall.app_presenter.view.ILoadingPageListView;
import com.weknowall.app_presenter.view.IPageList;

/**
 * User: laomao
 * Date: 2017-01-20
 * Time: 12-16
 */

public abstract class LoadingPageListPresenter<RQ extends Listable,RPM,RP,RPML extends PageListModel<RPM>,RPL extends PageList<RP>,RPLM extends PageListMapper<RP,RPM,RPL,RPML,? extends MapperImpl<RP,RPM>>,V extends ILoadingPageListView<RP>> extends LoadingPageListBasePresenter<RQ,RQ,RPML,RPL,V> {

    private RQ mListable;
    private RPLM mRplm;

    public LoadingPageListPresenter(@NonNull UseCase<RQ, RPML> useCase,RPLM rplm) {
        super(useCase);
        mRplm = rplm;
    }

    @Override
    public void initialize(RQ... rqs) {
        mListable = rqs[0];
        execute(mListable);
        onPageListLoading(mListable.getLoadType());
    }

    @Override
    public void onNext(RPML rpml) {
        super.onNext(rpml);
        RPL data = mRplm.transform(rpml);
        if (data.getTotalPage()==0){
            onPageListStateChanged(IPageList.STATE_EMPTY);
            onGet(data);
            return;
        }

        if (mListable.isFirstPage()){
            onGet(data);
        }else {
            onMore(data);
        }

        if (data.getCurrentPage()<data.getTotalPage()){
            // 还可以加载更多
            mListable.setPage(data.getCurrentPage()+1);
            mListable.setRealPage(mListable.getRealPage()+1);
            onPageListLoading(IPageList.STATE_LOADING);
        }else {
            onPageListStateChanged(IPageList.STATE_NOMORE);
        }

    }

    @Override
    protected boolean isInitialLoading() {
        return mListable != null && mListable.isFirstInitial();
    }

    private void onMore(RPL data) {
        getView().onMore(data.getItems());
    }

    private void onGet(RPL data) {
        if (data!=null){
            if (getView()!=null){
                getView().onGet(data.getItems());
            }

            if (data.getItems()==null||data.getItems().size()==0){
                onPageListStateChanged(IPageList.STATE_EMPTY);
            }
        }else {
            onPageListStateChanged(IPageList.STATE_EMPTY);
        }
    }
}
