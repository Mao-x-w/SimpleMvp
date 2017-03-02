package com.weknowall.app_data.net.api.builder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: Joy
 * Date: 2016/10/9
 * Time: 9:31
 */

public class OldInterceptor2 implements Interceptor {

	public static OldInterceptor2 create() {
		return new OldInterceptor2();
	}


	private OldInterceptor2() {

	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = OldInterceptorUtils.createRequestBuilder(chain);
		return OldInterceptorUtils.createResponse2(builder.build(), chain);
	}
}
