package com.weknowall.app_domain.interactor;

import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;

/**
 * User: laomao
 * Date: 2017-01-17
 * Time: 12-25
 * @param <R> repository
 */

public abstract class UseCaseImpl<R,RQM,RPM> extends UseCase<RQM, RPM> {

    private R mRepository;

    /**
     * 构造参数
     *
     * @param workThread   工作线程
     * @param resultThread 结果执行线程
     */
    public UseCaseImpl(R repository,ThreadExecutor workThread, PostExecutionThread resultThread) {
        super(workThread, resultThread);
        mRepository = repository;
    }

    public R getRepository(){
        return mRepository;
    }

}
