package com.weknowall.app_data.store.general;

import com.weknowall.app_data.entity.general.GitUserEntity;
import com.weknowall.app_data.store.IDataStore;

import java.util.List;

import rx.Observable;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 11-01
 */

public interface IGeneralDataStore extends IDataStore {

    Observable<List<GitUserEntity>> getGitUsers();

}
