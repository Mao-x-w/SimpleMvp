package com.weknowall.app_presenter.entity.general;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: JiYu
 * Date: 2016-08-18
 * Time: 16-21
 */

public class Image implements Parcelable{

	public String getBigUrl() {
		return bigUrl;
	}

	public void setBigUrl(String bigUrl) {
		this.bigUrl = bigUrl;
	}

	/**
	 * 资源类型 网络url、本地文件
	 */
	public enum Source{
		Net, File
	}
	private int width;
	private int height;

	private String url;
	private String thumbNailUrl;
	private String bigUrl;

	private Source source;

	public Image() {
	}

	public Image(int width, int height, String url, String thumbNailUrl) {
		this.width = width;
		this.height = height;
		this.url = url;
		this.thumbNailUrl = thumbNailUrl;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbNailUrl() {
		return thumbNailUrl;
	}

	public void setThumbNailUrl(String thumbNailUrl) {
		this.thumbNailUrl = thumbNailUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.width);
		dest.writeInt(this.height);
		dest.writeString(this.url);
		dest.writeString(this.thumbNailUrl);
		dest.writeString(this.bigUrl);
		dest.writeInt(this.source == null ? -1 : this.source.ordinal());
	}

	protected Image(Parcel in) {
		this.width = in.readInt();
		this.height = in.readInt();
		this.url = in.readString();
		this.thumbNailUrl = in.readString();
		this.bigUrl = in.readString();
		int tmpSource = in.readInt();
		this.source = tmpSource == -1 ? null : Source.values()[tmpSource];
	}

	public static final Creator<Image> CREATOR = new Creator<Image>() {
		@Override
		public Image createFromParcel(Parcel source) {
			return new Image(source);
		}

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
	};
}
