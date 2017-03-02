package com.weknowall.app_data.store;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 10-19
 */
@Singleton
public class DataStoreFactoryImpl<D extends IDataStore> {

    private Context mContext;
    private D mApiDataStore;
    private D mCacheDataStore;

    /**
     * 构造参数
     * @param context      context
     * @param apiDataStore 需要生成的ApiDataStore
     */
    @Inject
    public DataStoreFactoryImpl(Context context, D apiDataStore, D cacheDataStore) {
        mContext = context;
        mApiDataStore = apiDataStore;
        mCacheDataStore = cacheDataStore;
    }


    public Context getContext() {
        return mContext;
    }

    /**
     * 获取网络接口Store
     */
    public D getNetDataStore() {
        return mApiDataStore;
    }

    /**
     * 获取本地缓存Store
     */
    public D getCacheDataStore() {
        return mCacheDataStore;
    }
}
