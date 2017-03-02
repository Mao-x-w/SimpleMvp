package com.weknowall.app_presenter.dagger.modules;

import com.weknowall.app_domain.entity.general.GitUserModel;
import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;
import com.weknowall.app_domain.interactor.UseCase;
import com.weknowall.app_domain.interactor.general.GitUsersUseCase;
import com.weknowall.app_domain.repository.IGeneralRepository;
import com.weknowall.app_presenter.Constants;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 16-30
 */
@Module
public class GeneralModule {

    @Provides
    @Named(Constants.NAMED_GIT_USERS)
    UseCase<Object,List<GitUserModel>> provideGitUsersUseCase(IGeneralRepository repository, ThreadExecutor executor, PostExecutionThread thread){
        return new GitUsersUseCase(repository,executor,thread);
    }

}
