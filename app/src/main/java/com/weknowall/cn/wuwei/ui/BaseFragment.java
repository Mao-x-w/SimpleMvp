package com.weknowall.cn.wuwei.ui;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.weknowall.app_presenter.dagger.modules.FragmentModule;
import com.weknowall.app_presenter.view.ILoadingView;
import com.weknowall.cn.wuwei.CustomApplication;
import com.weknowall.cn.wuwei.dagger.components.ApplicationComponent;

/**
 * User: laomao
 * Date: 2017-02-13
 * Time: 12-19
 */

public class BaseFragment extends Fragment implements ILoadingView {


    protected ApplicationComponent getApplicationComponent() {
        return ((CustomApplication) getActivity().getApplication()).getComponent();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    private BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void startActivity(Class<? extends BaseActivity> activityClass) {
        getBaseActivity().startActivity(new Intent(getContext(), activityClass));
    }


    @Override
    public void showLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        if (getBaseActivity()!=null)
            getBaseActivity().dismissLoadingDialog();
    }

    @Override
    public void showMessage(String message) {
        if (getBaseActivity()!=null)
            getBaseActivity().showToast(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (getBaseActivity()!=null)
            getBaseActivity().showToast(resId);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onError(Class clazz) {

    }

    @Override
    public void onFinish() {

    }
}
