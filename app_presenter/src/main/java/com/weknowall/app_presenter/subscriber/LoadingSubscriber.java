package com.weknowall.app_presenter.subscriber;

import com.weknowall.app_domain.DefaultSubscriber;

import rx.Observer;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 18-09
 */

public class LoadingSubscriber<T> extends DefaultSubscriber<T> {


    private Observer<T> mObserver;

    public LoadingSubscriber(Observer<T> observer) {
        mObserver = observer;
    }

    @Override
    public void onNext(T t) {
        mObserver.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        mObserver.onError(e);
    }

    @Override
    public void onCompleted() {
        mObserver.onCompleted();
    }
}
