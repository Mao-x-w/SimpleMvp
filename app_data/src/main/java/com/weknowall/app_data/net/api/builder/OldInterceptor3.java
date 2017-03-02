package com.weknowall.app_data.net.api.builder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: laomao
 * Date: 2016-11-16
 * Time: 16-39
 */

public class OldInterceptor3 implements Interceptor {
    public static OldInterceptor3 create(){
        return new OldInterceptor3();
    }

    public OldInterceptor3() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = OldInterceptorUtils.createRequestBuilder(chain);
        return OldInterceptorUtils.createResponse3(builder.build(), chain);
    }
}
