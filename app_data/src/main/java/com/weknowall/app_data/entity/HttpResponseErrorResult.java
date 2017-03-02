package com.weknowall.app_data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * UserModel: JiYu
 * Date: 2016-09-01
 * Time: 16-32
 */

public class HttpResponseErrorResult implements Parcelable {

	@JSONField(name = "error_code")
	private int errorCode;

	@JSONField(name = "error_message")
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.errorCode);
		dest.writeString(this.message);
	}

	public HttpResponseErrorResult() {
	}

	protected HttpResponseErrorResult(Parcel in) {
		this.errorCode = in.readInt();
		this.message = in.readString();
	}

	public static final Creator<HttpResponseErrorResult> CREATOR = new Creator<HttpResponseErrorResult>() {
		@Override
		public HttpResponseErrorResult createFromParcel(Parcel source) {
			return new HttpResponseErrorResult(source);
		}

		@Override
		public HttpResponseErrorResult[] newArray(int size) {
			return new HttpResponseErrorResult[size];
		}
	};
}
