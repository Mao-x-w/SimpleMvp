package com.weknowall.app_data.mapper.general;

import com.weknowall.app_common.utils.MapperImpl;
import com.weknowall.app_data.entity.general.GitUserEntity;
import com.weknowall.app_domain.entity.general.GitUserModel;

import javax.inject.Inject;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-02
 */

public class GitUserEntityMapper extends MapperImpl<GitUserEntity,GitUserModel> {

    @Inject
    public GitUserEntityMapper() {
    }

    @Override
    public GitUserEntity transform(GitUserModel g) {
        GitUserEntity ge=new GitUserEntity();
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
    public GitUserModel transformTo(GitUserEntity g) {
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
