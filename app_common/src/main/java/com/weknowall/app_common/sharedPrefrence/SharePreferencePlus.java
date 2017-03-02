package com.weknowall.app_common.sharedPrefrence;

import android.content.Context;

public class SharePreferencePlus{

	private Context context;
	private SharePreferenceHelper shareHelp;

	public SharePreferencePlus(String name){
		shareHelp = SharePreferenceHelper.getInstance(name);
	}

	public SharePreferencePlus(Context context, String name){
		this.context = context;
		shareHelp = new SharePreferenceHelper(context, name);
	}

	public boolean clear() {
		return shareHelp.clear();
	}

	protected int getIntValue(String name, int defaultValue) {
		return shareHelp.getIntValue(name, defaultValue);
	}

	protected boolean editIntValue(String name, int value) {
		return shareHelp.editIntValue(name, value);
	}

	protected String getStringValue(String name, String defaultValue) {
		return shareHelp.getStringValue(name, defaultValue);
	}

	protected boolean editStringValue(String name, String value) {
		return shareHelp.editStringValue(name, value);
	}

	protected boolean getBooleanValue(String name, boolean defaultValue) {
		return shareHelp.getBooleanValue(name, defaultValue);
	}

	protected boolean editBooleanValue(String name, boolean value) {
		return shareHelp.editBooleanValue(name, value);
	}

	protected float getFloatValue(String name, float defaultValue) {
		return shareHelp.getFloatValue(name, defaultValue);
	}

	protected boolean editFloatValue(String name, float value) {
		return shareHelp.editFloatValue(name, value);
	}
	
	protected long getLongValue(String name, long defaultValue){
		return shareHelp.getLongValue(name, defaultValue);
	}
	
	protected boolean editLongValue(String name, long value){
		return shareHelp.editLongValue(name, value);
	}

	public Context getContext() {
		return context;
	}
}
