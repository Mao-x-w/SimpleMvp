package com.weknowall.app_presenter.presenter.general;

import android.support.annotation.NonNull;

import com.weknowall.app_domain.entity.general.GitUserModel;
import com.weknowall.app_domain.interactor.UseCase;
import com.weknowall.app_presenter.Constants;
import com.weknowall.app_presenter.entity.general.GitUser;
import com.weknowall.app_presenter.mapper.general.GitUserMapper;
import com.weknowall.app_presenter.presenter.LoadingPresenter;
import com.weknowall.app_presenter.view.IGitUsersView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-19
 */

public class GitUsersPresenter extends LoadingPresenter<Object,Object,List<GitUserModel>,List<GitUser>,IGitUsersView> {

    private GitUserMapper mGMapper;

    @Inject
    public GitUsersPresenter(
            @Named(Constants.NAMED_GIT_USERS)
            @NonNull UseCase<Object, List<GitUserModel>> useCase, GitUserMapper gMapper) {
        super(useCase);
        mGMapper = gMapper;
    }

    @Override
    public void initialize(Object... objects) {
        execute(objects);
    }

    @Override
    public void onNext(List<GitUserModel> gitUserModels) {
        super.onNext(gitUserModels);
        getView().onGetGitUsers(mGMapper.transform(gitUserModels));
    }
}
