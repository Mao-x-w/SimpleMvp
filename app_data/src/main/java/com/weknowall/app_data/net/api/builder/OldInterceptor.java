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

public class OldInterceptor implements Interceptor {

	public static OldInterceptor create() {
		return new OldInterceptor();
	}


	private OldInterceptor() {

	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = OldInterceptorUtils.createRequestBuilder(chain);

		return OldInterceptorUtils.createResponse(builder.build(), chain);
	}
}
