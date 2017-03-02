package com.weknowall.cn.wuwei.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.app_presenter.dagger.modules.ActivityModule;
import com.weknowall.app_presenter.view.ILoadingView;
import com.weknowall.cn.wuwei.CustomApplication;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.dagger.components.ApplicationComponent;
import com.weknowall.cn.wuwei.utils.UiHelper;

/**
 * User: laomao
 * Date: 2016-12-21
 * Time: 11-36
 */

public class BaseActivity extends AppCompatActivity implements ILoadingView{

    private UiHelper mUiHelper;
    protected Toolbar mToolbar;


    public Context getContext(){
        return this;
    }

    public void startActivity(Class<? extends BaseActivity> clazz){
        startActivity(new Intent(getContext(),clazz));
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(v -> finish());
        }
    }


    /**
     * Dagger application component
     */
    public ApplicationComponent getApplicationComponent() {
        return ((CustomApplication) getApplication()).getComponent();
    }

    /**
     * Dagger activity module
     */
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    protected synchronized UiHelper getUiHelper() {
        synchronized (BaseActivity.class) {
            if (mUiHelper == null) {
                synchronized (BaseActivity.class) {
                    mUiHelper = new UiHelper(this);
                }
            }
        }
        return mUiHelper;
    }

    /**
     * 隐藏输入框
     */
    public void hideInput() {
        if (DeviceHelper.isInputDisplaying(getContext())) {
            DeviceHelper.toggleInput(getCurrentFocus(), false);
        }
    }


    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showToast(resId);
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

    protected void showLoadingDialog() {
        getUiHelper().showLoadingDialog();
    }

    protected void dismissLoadingDialog() {
        getUiHelper().dismissLoadingDialog();
    }

    protected void showToast(String message) {
        getUiHelper().showToast(message);
    }

    protected void showToast(int resId) {
        getUiHelper().showToast(getString(resId));
    }
}
