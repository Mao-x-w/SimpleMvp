package com.weknowall.app_data.store;

import com.weknowall.app_data.cahe.ICache;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 11-13
 */

@Singleton
public class CacheDataStoreImpl<C extends ICache> implements IDataStore {

    private C mCache;

    @Inject
    public CacheDataStoreImpl(C cache) {
        mCache = cache;
    }

    public C getCache() {
        return mCache;
    }
}
