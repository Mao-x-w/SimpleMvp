package com.weknowall.app_data.net.api.service;

import com.alibaba.fastjson.parser.Feature;
import com.weknowall.app_data.BuildConfig;
import com.weknowall.app_data.net.api.builder.OldInterceptor;
import com.weknowall.app_data.net.api.builder.OldInterceptor2;
import com.weknowall.app_data.net.api.builder.OldInterceptor3;
import com.weknowall.app_data.net.api.builder.OldListInterceptor;
import com.weknowall.app_data.net.api.builder.OldLoginInterceptor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 14-34
 */

public class BaseRetrofitNetImpl<T> {

    /**
     * 默认的解析参数
     */
    private static Feature[] parseFeature = {Feature.UseObjectArray};
    private T mServiceImpl;
    private Class<T> mServiceClass;

    public BaseRetrofitNetImpl() {
        // 获取泛型的class
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        mServiceClass = (Class<T>) (generics[0]);
    }

    /**
     * 普通的Retrofit实现
     * @return
     */
    protected T getService(){
       return mServiceImpl==null?mServiceImpl=createRetrofit().create(mServiceClass):mServiceImpl;
    }

    /**
     * 用户登录专用的Retrofit service实现
     */
    protected T getLoginService(String username, String password) {
        return createDefaultBuilder().client(new OkHttpClient.Builder().addInterceptor(OldLoginInterceptor.create(username, password)).build())
                .build()
                .create(mServiceClass);
    }

    /**
     * "data" 字段为 "imgs_news" 老接口上传图片用
     * @return return
     */
    T getService3() {

        return createDefaultBuilder().client(new OkHttpClient.Builder()
                .addInterceptor(OldInterceptor3.create()).build()).build().create(mServiceClass);
    }

    /**
     * "data" 字段为 "obj" 的老接口使用
     * @return return
     */
    T getService2() {
        return createDefaultBuilder().client(new OkHttpClient.Builder().addInterceptor(OldInterceptor2.create()).build()).build().create(mServiceClass);
    }

    /**
     * 生成不自动获取item数据的列表接口
     * @return return
     */
    T getListService() {
        return createDefaultBuilder().client(new OkHttpClient.Builder().addInterceptor(OldListInterceptor.create()).build()).build().create(mServiceClass);
    }

    private Retrofit createRetrofit() {
        return createDefaultBuilder().client(new OkHttpClient.Builder().addInterceptor(OldInterceptor.create()).build()).build();
    }

    private Retrofit.Builder createDefaultBuilder() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create().setParserFeatures(parseFeature))
                .baseUrl(BuildConfig.HTTP_HOST);
    }
}
