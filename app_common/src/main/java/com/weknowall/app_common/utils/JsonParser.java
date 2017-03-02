package com.weknowall.app_common.utils;

import com.alibaba.fastjson.JSON;
import com.weknowall.app_common.zip.commons.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: JiYu
 * Date: 2016-09-18
 * Time: 17-36
 */

public class JsonParser {

	public static String getString(String json, String key){
		return JSON.parseObject(json).getString(key);
	}

	public static <T> T parseObject(String str, Class<T> clz) {
		return JSON.parseObject(str, clz);
	}

	public static <T> T parseObject(InputStream in, Class<T> clz) {
		try {
			return JSON.parseObject(in, clz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> parseArray(InputStream in, Class<T> clz) {
		try {
			return JSON.parseArray(IOUtils.toString(in, "UTF-8"), clz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> parseArray(String str, Class<T> clz) {
		return JSON.parseArray(str, clz);
	}

	public static <T> String toJson(T t) {
		return JSON.toJSONString(t);
	}

	public static <T> void writeJson(OutputStream o, T t) {
		try {
			JSON.writeJSONString(o, t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
