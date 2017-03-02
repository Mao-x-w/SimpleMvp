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

public class OldListInterceptor implements Interceptor {

	public static OldListInterceptor create() {
		return new OldListInterceptor();
	}


	private OldListInterceptor() {

	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = OldInterceptorUtils.createRequestBuilder(chain);

		return OldInterceptorUtils.createListResponse(builder.build(), chain);
	}
}
