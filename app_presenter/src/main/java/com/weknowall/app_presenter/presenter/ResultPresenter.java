package com.weknowall.app_presenter.presenter;

import android.support.annotation.NonNull;

import com.weknowall.app_domain.interactor.UseCase;
import com.weknowall.app_presenter.subscriber.LoadingSubscriber;
import com.weknowall.app_presenter.view.IResultView;

import rx.Observer;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 18-33
 */

public abstract class ResultPresenter<RQM,RQ,RPM,RP,V extends IResultView> implements IPresenter,IResultView, Observer<RPM> {

    private V view;
    private UseCase<RQM, RPM> mUseCase;

    public ResultPresenter(@NonNull UseCase<RQM,RPM> useCase) {
        mUseCase = useCase;
    }

    public void setView(@NonNull V view) {
        this.view = view;
    }

    public V getView(){
        return view;
    }

    public UseCase<RQM, RPM> getUseCase() {
        return mUseCase;
    }

    public abstract void initialize(RQ... rqs);

    protected void execute(RQM... rqm){
        getUseCase().execute(new LoadingSubscriber<RPM>(this),rqm);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getUseCase().unSubscribe();
    }


    /**
     * Observable中调用onCompleted{@link Observer#onCompleted()}
     */
    @Override
    public void onCompleted() {
        onSuccess();
        onFinish();
    }

    /**
     * Observable中调用onError{@link Observer#onError(Throwable)}
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onError();
        onFinish();
    }

    /**
     * Observable中调用onNext{@link Observer#onNext(Object)}
     */
    @Override
    public void onNext(RPM rpm) {

    }



    /**
     * View中调用
     */
    @Override
    public void onSuccess() {
        if (getView()!=null)
            getView().onSuccess();
    }

    /**
     * View中调用
     */
    @Override
    public void onError() {
        if (getView()!=null)
            getView().onError();
        onError(getClass());
    }

    /**
     * View中调用
     */
    @Override
    public void onError(Class clazz) {
        if (getView()!=null)
            getView().onError(clazz);
    }

    /**
     * View中调用
     */
    @Override
    public void onFinish() {
        if (getView()!=null)
            getView().onFinish();
    }

}
