package com.weknowall.app_data.net.api.service;

import com.weknowall.app_data.entity.general.GitUserEntity;
import com.weknowall.app_data.net.api.aidl.IGeneralRetrofit;
import com.weknowall.app_data.net.general.IGeneralNet;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 15-33
 */

public class GeneralRetrofitNetImpl extends BaseRetrofitNetImpl<IGeneralRetrofit> implements IGeneralNet {

    @Inject
    public GeneralRetrofitNetImpl() {
    }

    @Override
    public Observable<List<GitUserEntity>> getGitUsers() {
        return getService().getGitUsers();
    }
}
