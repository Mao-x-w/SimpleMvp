package com.weknowall.app_domain.repository;

import com.weknowall.app_domain.entity.general.GitUserModel;

import java.util.List;

import rx.Observable;

/**
 * User: laomao
 * Date: 2017-01-17
 * Time: 11-48
 */

public interface IGeneralRepository extends IRepository {

    Observable<List<GitUserModel>> getGitUsers();

}
