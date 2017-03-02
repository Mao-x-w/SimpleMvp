package com.weknowall.app_presenter.view;

import com.weknowall.app_presenter.entity.general.GitUser;

import java.util.List;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-21
 */

public interface IGitUsersView extends ILoadingView{

    void onGetGitUsers(List<GitUser> users);

}
