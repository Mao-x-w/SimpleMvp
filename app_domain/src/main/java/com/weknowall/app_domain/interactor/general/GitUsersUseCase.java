package com.weknowall.app_domain.interactor.general;

import com.weknowall.app_domain.entity.general.GitUserModel;
import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;
import com.weknowall.app_domain.interactor.UseCaseImpl;
import com.weknowall.app_domain.repository.IGeneralRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-15
 */

@Singleton
public class GitUsersUseCase extends UseCaseImpl<IGeneralRepository,Object,List<GitUserModel>> {

    /**
     * 构造参数
     *
     * @param repository
     * @param workThread   工作线程
     * @param resultThread 结果执行线程
     */
    @Inject
    public GitUsersUseCase(IGeneralRepository repository, ThreadExecutor workThread, PostExecutionThread resultThread) {
        super(repository, workThread, resultThread);
    }

    @Override
    protected Observable<List<GitUserModel>> buildUseCaseObservable(Object... objects) {
        return getRepository().getGitUsers();
    }
}
