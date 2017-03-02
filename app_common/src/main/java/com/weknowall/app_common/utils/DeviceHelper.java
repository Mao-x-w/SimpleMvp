package com.weknowall.app_common.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.weknowall.app_common.Configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * **********************
 * Author: yu
 * Date:   2015/8/5
 * Time:   17:42
 * **********************
 */
public class DeviceHelper {

	private static Context getContext() {
		return Configuration.getInstance().getContext();
	}

	/**
	 * 获取设备id
	 */
	public static String getDeviceId() {
		TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static DisplayMetrics getDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
		return dm;
	}


	/**
	 * dp转换px
	 */
	public static int dp2Px(float dp) {
		return (int) (dp * getDisplayMetrics().density + 0.5f);
	}

	/**
	 * 根据屏幕密度以及最短边的比例, 将dp转换等比例px
	 * @param dp dp
	 */
	public static int dp2bestPx(float dp) {
		return (int) (dp * (float) Math.min(getDisplayMetrics().widthPixels, getDisplayMetrics().heightPixels) / 480f);
	}


	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenWidth() {
		return getScreenWidth(getContext());
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public static int getScreenHeight() {
		return getScreenHeight(getContext());
	}

	/**
	 * 获取进程的名称
	 */
	public static String getCurrentProcessName(Context context) {
		int pid = android.os.Process.myPid();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		if (activityManager == null) {
			return null;
		}

		List<ActivityManager.RunningAppProcessInfo> runningProcessList = activityManager.getRunningAppProcesses();
		if (runningProcessList == null || runningProcessList.isEmpty()) {
			return null;
		}

		for (ActivityManager.RunningAppProcessInfo appProcess : runningProcessList) {
			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	/**
	 * 判断是否是远程进程信息
	 * @return true 代表远程   false 代表不是远程
	 */
	public static boolean isRemoteProcess(Context context) {
		final String processName = getCurrentProcessName(context);
		return !TextUtils.isEmpty(processName) && processName.contains(":");
	}

	public static PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
		// 获取packageManager的实例
		PackageManager packageManager = getContext().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		return packageManager.getPackageInfo(getContext().getPackageName(), 0);
	}

	/**
	 * 获取版本名称
	 */
	public static String getVersionName() {
		String version = "";
		try {
			version = getPackageInfo().versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 获取版本号
	 */
	public static int getVersionCode() {
		int versionCode = 1;
		try {
			versionCode = getPackageInfo().versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 键盘隐藏和弹出
	 * @param v      v
	 * @param isShow isShow
	 */
	@Deprecated
	public static void toggelInput(View v, boolean isShow) {
		toggleInput(v, isShow);
	}

	/**
	 * 键盘隐藏和弹出
	 * @param v      v
	 * @param isShow isShow
	 */
	public static void toggleInput(View v, boolean isShow) {
		if (v == null)
			return;
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (isShow) {
			imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
		} else {
			if (imm.isActive())
				imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	public static boolean isInputDisplaying(Context context) {
		return ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).isActive();
	}

	public static boolean isAppForeground() {
		return isAppForeground(getContext());
	}

	/**
	 * 判断当前应用是否在前台运行
	 * @param context context
	 */
	public static boolean isAppForeground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回当前设备是否已经ROOT
	 */
	public static boolean isDeviceRooted() {
		if (checkRootMethod1()) {
			return true;
		}
		if (checkRootMethod2()) {
			return true;
		}
		if (checkRootMethod3()) {
			return true;
		}
		return false;
	}

	//	/**
	//	 * 获取设备的IP地址
	//	 */
	//	public static String getDeviceHost() {
	//		String host;
	//		WiFiManager wifiUtils = new WiFiManager(Joy.getContext());
	//		if (wifiUtils.isWIfiConnected()) {
	//			host = wifiUtils.getLocalHost();
	//		} else {
	//			host = getDeviceIPAddress();
	//		}
	//		return host;
	//	}


	public enum NetworkType {
		None, Wifi, Mobile, UnKnow
	}

	/**
	 * 当前网络是否可用
	 */
	public static boolean isNetworkAvailable() {
		return getContext() != null && getNetworkType(getContext()) != NetworkType.None;
	}

	public static boolean isNetworkAvailable(Context context) {
		return getNetworkType(context) != NetworkType.None;
	}

	/**
	 * 获取当前网络状态
	 * @param context context
	 * @return None:无网络连接;Mobile:手机网络;Wifi:Wifi网络;UnKnow:未知网络连接
	 */
	public static NetworkType getNetworkType(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NetworkType.None;
		}

		switch (networkInfo.getType()) {
			case ConnectivityManager.TYPE_MOBILE:
				return NetworkType.Mobile;
			case ConnectivityManager.TYPE_WIFI:
				return NetworkType.Wifi;
			default:
				return NetworkType.UnKnow;
		}
	}


	private static String getDeviceIPAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取本机号码
	 * @return
	 */
	public static String getLocalPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	private static boolean checkRootMethod1() {
		String buildTags = android.os.Build.TAGS;
		if (buildTags != null && buildTags.contains("test-keys")) {
			return true;
		}
		return false;
	}

	private static boolean checkRootMethod2() {
		try {
			File file = new File("/system/app/Superuser.apk");
			if (file.exists()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	private static boolean checkRootMethod3() {
		if (new ExecShell().executeCommand(ExecShell.SHELL_CMD.check_su_binary) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Kevin Kowalewski
	 */
	public static class ExecShell {
		public static enum SHELL_CMD {
			check_su_binary(new String[]{"/system/xbin/which", "su"});
			String[] command;

			SHELL_CMD(String[] command) {
				this.command = command;
			}
		}

		public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
			String line = null;
			ArrayList<String> fullResponse = new ArrayList<String>();
			Process localProcess = null;
			try {
				localProcess = Runtime.getRuntime().exec(shellCmd.command);
			} catch (Exception e) {
				return null;
			}
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
			try {
				while ((line = in.readLine()) != null) {
					fullResponse.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fullResponse;
		}
	}

	/**
	 * 打电话
	 * @param context context
	 * @param number  number
	 */
	public static void call(Context context, String number) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + number));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 浏览器
	 * @param context context
	 * @param uri     uri
	 */
	public static void browser(Context context, String uri) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://" + uri));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 发短信
	 * @param context context
	 * @param number  number
	 * @param content content
	 */
	public static void msg(Context context, String number, String content) {
		Uri uri = Uri.parse("smsto://" + number);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 获取缓存目录
	 * @param context context
	 * @return 如果内存卡存在，则获取内存卡中缓存目录，否则获取设备内存缓存目录
	 */
	public static String getCacheDir(Context context) {
		File file = context.getExternalCacheDir();
		if (file != null && !TextUtils.isEmpty(file.getAbsolutePath())) {
			return file.getAbsolutePath();
		} else {
			return context.getCacheDir().getAbsolutePath();
		}
	}

	public static String getCacheDir() {
		return getCacheDir(getContext());
	}

	/**
	 * 获取文件目录
	 * @param context context
	 * @return 如果内存卡存在，则获取内存卡中文件目录，否则获取设备内存文件目录
	 */
	public static String getFileDir(Context context) {
		File file = context.getExternalFilesDir(null);
		if (file != null && !TextUtils.isEmpty(file.getAbsolutePath())) {
			return file.getAbsolutePath();
		} else {
			return context.getFilesDir().getAbsolutePath();
		}
	}

	public static String getFileDir() {
		return getFileDir(getContext());
	}

	/**
	 * checkPermissions
	 *
	 * @param context
	 * @param permission
	 * @return true or false
	 */
	public static boolean checkPermissions(Context context, String permission) {
		PackageManager localPackageManager = context.getPackageManager();
		return localPackageManager.checkPermission(permission,
				context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
	}
}
