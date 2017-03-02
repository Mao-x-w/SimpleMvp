package com.weknowall.app_presenter.dagger.modules;

import android.app.Activity;

import com.weknowall.app_presenter.dagger.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 16-22
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mActivity;
    }
}
