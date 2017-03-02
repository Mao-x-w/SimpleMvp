package com.weknowall.app_data.net.api.builder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: Joy
 * Date: 2016/11/3
 * Time: 16:26
 */

public class OldLoginInterceptor implements Interceptor {


	public static OldLoginInterceptor create(String username, String password) {
		return new OldLoginInterceptor(username, password);
	}

	private final String username;
	private final String password;

	private OldLoginInterceptor(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = OldInterceptorUtils.createRequestBuilder(chain);

		return OldInterceptorUtils.createResponse(builder.build(), chain);
	}
}
