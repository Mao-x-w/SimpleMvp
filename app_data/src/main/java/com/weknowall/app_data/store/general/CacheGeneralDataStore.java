package com.weknowall.app_data.store.general;

import com.weknowall.app_data.cahe.general.IGeneralCache;
import com.weknowall.app_data.entity.general.GitUserEntity;
import com.weknowall.app_data.store.CacheDataStoreImpl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 11-03
 */

@Singleton
public class CacheGeneralDataStore extends CacheDataStoreImpl<IGeneralCache> implements IGeneralDataStore {

    @Inject
    public CacheGeneralDataStore(IGeneralCache cache) {
        super(cache);
    }

    @Override
    public Observable<List<GitUserEntity>> getGitUsers() {
        return null;
    }
}
