package com.weknowall.cn.wuwei.widget.plus;

/**
 * User: JiYu
 * Date: 2016-09-21
 * Time: 09-34
 */

public interface IPlusFrame {

	void notifySuccess();

	void notifyFinish();

	void notifyError();

	void notifyEmpty();

	void notifyLoading(LoadingType type);
}
