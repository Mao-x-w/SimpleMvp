package com.weknowall.app_data.repository;

import com.weknowall.app_data.store.DataStoreFactoryImpl;
import com.weknowall.app_data.store.IDataStore;
import com.weknowall.app_domain.executor.ThreadExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 10-36
 */

@Singleton
public class RepositoryImpl<D extends IDataStore,F extends DataStoreFactoryImpl<D>> {

    private F mDataStoreFactory;
    private ThreadExecutor threadExecutor;

    @Inject
    public RepositoryImpl(F dataStoreFactory) {
        mDataStoreFactory = dataStoreFactory;
    }

    @Inject
    public void provideThreadExecutor(ThreadExecutor threadExecutor) {
        this.threadExecutor = threadExecutor;
    }

    public F getDataStoreFactory() {
        return mDataStoreFactory;
    }

    D getNetDataStore(){
        return mDataStoreFactory.getNetDataStore();
    }

    D getCacheDataStore(){
        return mDataStoreFactory.getCacheDataStore();
    }

    /**
     * 先从缓存加载数据然后再从网络中加载
     * @param cache 缓存加载项
     * @param api 网络加载项
     * @param <T> 泛型
     * @return
     */
    <T> Observable<T> getCacheThenApi(Observable<T> cache, Observable<T> api){
        return Observable.create(sb -> {
            cache.subscribe(t -> {
                sb.onNext(t);
                executeApi(sb, api);
            },e -> {
                executeApi(sb, api);
            });
        });
    }

    /**
     * 从网络加载数据
     * @param sb
     * @param api
     * @param <T>
     */
    private <T> void executeApi(Subscriber<? super T> sb, Observable<T> api) {
        api.subscribeOn(Schedulers.from(threadExecutor))
                .subscribe(sb::onNext,sb::onError,sb::onCompleted);
    }
}
