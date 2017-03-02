package com.weknowall.app_presenter.mapper.general;

import com.weknowall.app_common.utils.MapperImpl;
import com.weknowall.app_domain.entity.general.GitUserModel;
import com.weknowall.app_presenter.entity.general.GitUser;

import javax.inject.Inject;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-04
 */

public class GitUserMapper extends MapperImpl<GitUser,GitUserModel> {

    @Inject
    public GitUserMapper() {
    }

    @Override
    public GitUser transform(GitUserModel g) {
        GitUser ge=new GitUser();
        ge.setAvatar(g.getAvatar());
        ge.setFollowersUrl(g.getFollowersUrl());
        ge.setFollowingUrl(g.getFollowingUrl());
        ge.setId(g.getId());
        ge.setName(g.getName());
        ge.setUserHtmlUrl(g.getUserHtmlUrl());
        ge.setUserUrl(g.getUserUrl());
        return ge;
    }

    @Override
    public GitUserModel transformTo(GitUser g) {
        GitUserModel ge=new GitUserModel();
        ge.setAvatar(g.getAvatar());
        ge.setFollowersUrl(g.getFollowersUrl());
        ge.setFollowingUrl(g.getFollowingUrl());
        ge.setId(g.getId());
        ge.setName(g.getName());
        ge.setUserHtmlUrl(g.getUserHtmlUrl());
        ge.setUserUrl(g.getUserUrl());
        return ge;
    }
}
