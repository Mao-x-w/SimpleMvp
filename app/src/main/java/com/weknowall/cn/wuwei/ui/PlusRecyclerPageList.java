package com.weknowall.cn.wuwei.ui;

import com.weknowall.app_presenter.view.IPageList;
import com.weknowall.cn.wuwei.widget.plus.IPlusFrame;
import com.weknowall.cn.wuwei.widget.plus.LoadingType;

/**
 * User: laomao
 * Date: 2016-09-21
 * Time: 11-07
 */

public class PlusRecyclerPageList implements IPageList {

	private IPlusFrame recycler;

	public static PlusRecyclerPageList newInstance(IPlusFrame recycler) {
		return new PlusRecyclerPageList(recycler);
	}

	private PlusRecyclerPageList(IPlusFrame recycler) {
		this.recycler = recycler;
	}

	@Override
	public void onPageStateChanged(int state) {
		switch (state) {
			case STATE_EMPTY:
				recycler.notifyEmpty();
				break;
			case STATE_ERROR:
				recycler.notifyError();
				break;
			case STATE_LOADING:
				recycler.notifySuccess();
				break;
			case STATE_NOMORE:
				recycler.notifyFinish();
				break;
			case STATE_NONE:
				break;
		}
	}

	@Override
	public void onLoading(int loadType) {
		LoadingType type = null;
		switch (loadType) {
			case LOADING_TYPE_GET:
				type = LoadingType.Get;
				break;
			case LOADING_TYPE_MORE:
				type = LoadingType.More;
				break;
			case LOADING_TYPE_REFRESH:
				type = LoadingType.Refresh;
				break;
		}
		recycler.notifyLoading(type);
	}
}
