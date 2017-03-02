package com.weknowall.app_data.store.general;

import android.content.Context;

import com.weknowall.app_data.store.DataStoreFactoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 11-31
 */

@Singleton
public class GeneralDataStoreFactory extends DataStoreFactoryImpl<IGeneralDataStore> {

    /**
     * 构造参数
     *
     * @param context        context
     * @param apiDataStore   需要生成的ApiDataStore
     * @param cacheDataStore
     */
    @Inject
    public GeneralDataStoreFactory(Context context, NetGeneralDataStore apiDataStore, CacheGeneralDataStore cacheDataStore) {
        super(context, apiDataStore, cacheDataStore);
    }
}
