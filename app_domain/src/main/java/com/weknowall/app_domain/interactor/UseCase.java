package com.weknowall.app_domain.interactor;

import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * User: laomao
 * Date: 2017-01-17
 * Time: 12-10
 * @param <RQM> 请求的model
 * @param <RPM> 响应的model
 */

public abstract class UseCase<RQM,RPM> {

    private ThreadExecutor mWorkThread;
    private PostExecutionThread mResultThread;
    private Subscription mSubscription= Subscriptions.empty();

    /**
     * 构造参数
     * @param workThread 工作线程
     * @param resultThread   结果执行线程
     */
    public UseCase(ThreadExecutor workThread, PostExecutionThread resultThread) {
        mWorkThread = workThread;
        mResultThread = resultThread;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<RPM> buildUseCaseObservable(RQM... rqms);

    public void execute(Subscriber<RPM> rpmSubscriber,RQM... rqms){
        mSubscription = buildUseCaseObservable(rqms)
                .subscribeOn(Schedulers.from(mWorkThread))
                .observeOn(mResultThread.getScheduler())
                .unsubscribeOn(Schedulers.from(mWorkThread))
                .subscribe(rpmSubscriber);
    }

    public void unSubscribe(){
        mSubscription.unsubscribe();
    }
}
