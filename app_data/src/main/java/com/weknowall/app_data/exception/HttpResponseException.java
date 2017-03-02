package com.weknowall.app_data.exception;


import com.weknowall.app_data.entity.HttpResponseErrorResult;

import java.io.IOException;

/**
 * UserModel: JiYu
 * Date: 2016-09-01
 * Time: 16-29
 */

public class HttpResponseException extends IOException {

	private HttpResponseErrorResult result;

	public HttpResponseException(HttpResponseErrorResult result) {
		this.result = result;
	}

	@Override
	public String getMessage() {
		if (result != null) {
			return result.getMessage();
		}
		return super.getMessage();
	}
}
