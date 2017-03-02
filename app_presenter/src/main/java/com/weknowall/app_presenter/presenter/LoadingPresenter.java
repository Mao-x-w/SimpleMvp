package com.weknowall.app_presenter.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.weknowall.app_data.exception.HttpResponseException;
import com.weknowall.app_domain.interactor.UseCase;
import com.weknowall.app_presenter.view.ILoadingView;

/**
 * User: laomao
 * Date: 2017-01-19
 * Time: 10-19
 */

public abstract class LoadingPresenter<RQM, RQ, RPM, RP, V extends ILoadingView> extends ResultPresenter<RQM, RQ, RPM, RP, V> implements ILoadingView {

    private boolean canShowLoading = true;

    public LoadingPresenter(@NonNull UseCase<RQM, RPM> useCase) {
        super(useCase);
    }

    @Override
    protected void execute(RQM... rqm) {
        super.execute(rqm);
        showLoading();
    }


    /**
     * Observable加载数据过程中走的方法
     *
     * @param rpm
     */
    @Override
    public void onNext(RPM rpm) {
        if (rpm == null)
            return;
        hideLoading();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof HttpResponseException) {
            showMessage(e.getMessage());
        }
//        else {
//            showMessage(R.string.load_net_error);
//        }
        hideLoading();
    }


    /**
     * Loading框的显示与隐藏，以及信息提示  start
     */
    @Override
    public void showLoading() {
        if (getView() != null && canShowLoading)
            getView().showLoading();
    }

    @Override
    public void hideLoading() {
        if (getView() != null && canShowLoading)
            getView().hideLoading();
    }

    @Override
    public void showMessage(String message) {
        if (getView() != null)
            getView().showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (getView() != null)
            getView().showMessage(resId);
    }

    public void setCanShowLoading(boolean canShowLoading) {
        this.canShowLoading = canShowLoading;
    }

}
