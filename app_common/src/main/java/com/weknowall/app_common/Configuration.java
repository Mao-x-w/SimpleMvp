package com.weknowall.app_common;

import android.content.Context;
import android.text.TextUtils;

import com.weknowall.app_common.utils.Logs;

import java.io.File;

/**
 * User: JiYu
 * Date: 2016-08-10
 * Time: 13-34
 * msj配置工具  用来配置log、cache、file等相关事务
 */

public class Configuration {

	private static Configuration configuration;

	public static void init(Context context) {
		synchronized (Configuration.class) {
			if (configuration == null) {
				configuration = new Configuration(context);
			}
		}
	}

	public static Configuration newInstance(Context context){
		return new Configuration(context);
	}

	public static Configuration getInstance() {
		if (configuration == null) {
			throw new NullPointerException("please init first!!!");
		}
		return configuration;
	}

	private Context context;
	private String channelId;

	private Configuration(Context context) {
		this.context = context.getApplicationContext();
		//  初始化Log
		Logs.init();
	}

	/**
	 * 设置是否打印log
	 * @param debug debug
	 */
	public Configuration debug(boolean debug) {
		Logs.DEBUG = debug;
		return this;
	}

	/**
	 * 获取缓存总目录
	 * 可以用来存放图片、网络等缓存文件
	 * @return 缓存总目录
	 */
	public String getCachePath() {
		String cachePath;
		if (context.getExternalCacheDir() != null && !TextUtils.isEmpty(context.getExternalCacheDir().getAbsolutePath())) {
			cachePath = context.getExternalCacheDir().getAbsolutePath();
		} else {
			cachePath = context.getCacheDir().getAbsolutePath();
		}
		return cachePath;
	}

	/**
	 * 获取图片缓存路径
	 * @return 图片缓存路径
	 */
	public String getImageCachePath() {
		String imagePath = getCachePath() + "/";
		File f = new File(imagePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		return imagePath;
	}

	/**
	 * 获取文件存储总目录
	 * 可以用来存放下载的文件等
	 * @return 文件存储总目录
	 */
	public String getFilePath() {
		String filePath;
		if (context.getExternalFilesDir(null) != null && !TextUtils.isEmpty(context.getExternalCacheDir().getAbsolutePath())) {
			filePath = context.getExternalFilesDir(null).getAbsolutePath();
		} else {
			filePath = context.getCacheDir().getAbsolutePath();
		}
		return filePath;
	}

	public String getDownloadPath(){
		return getFilePath() + "/download";
	}

	public Context getContext() {
		return context;
	}

	/**
	 * 获取渠道ID
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * 设置渠道ID
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
