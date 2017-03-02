package com.weknowall.app_common.utils;

import com.orhanobut.logger.Logger;

/**
 * User: JiYu
 * Date: 2016-08-09
 * Time: 15-18
 */

public class Logs {

	public static boolean DEBUG = false;

	public static void init() {
		Logger.init("msj").methodCount(2).hideThreadInfo();
	}

	public static void d(Object obj) {
		if (DEBUG) {
			try {
				Logger.d(obj);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void d(String msg, Object... objs) {
		if (DEBUG) {
			try {
				Logger.d(msg, objs);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void e(String msg, Object... objs) {
		if (DEBUG) {
			try {
				Logger.e(msg, objs);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void i(String msg, Object... objs) {
		if (DEBUG) {
			try {
				Logger.i(msg, objs);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void v(String msg, Object... objs) {
		if (DEBUG) {
			try {
				Logger.v(msg, objs);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void j(String json) {
		if (DEBUG) {
			try {
				Logger.json(json);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
