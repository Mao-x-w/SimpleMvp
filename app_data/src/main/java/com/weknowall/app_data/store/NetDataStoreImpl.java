package com.weknowall.app_data.store;

import com.weknowall.app_data.cahe.ICache;
import com.weknowall.app_data.net.INet;

import javax.inject.Inject;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 11-09
 */

public class NetDataStoreImpl<N extends INet,C extends ICache> implements IDataStore {

    private N mService;
    private C mCache;

    @Inject
    public NetDataStoreImpl(N service, C cache) {
        mService = service;
        mCache = cache;
    }

    public N getService() {
        return mService;
    }

    public C getCache() {
        return mCache;
    }
}
