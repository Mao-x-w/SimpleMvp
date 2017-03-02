package com.weknowall.app_data.net.api.aidl;

import com.weknowall.app_data.Constant;
import com.weknowall.app_data.entity.general.GitUserEntity;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 14-28
 */

public interface IGeneralRetrofit {

    @GET(Constant.HttpHost.GIT_GOD_USERS)
    Observable<List<GitUserEntity>> getGitUsers();

}
