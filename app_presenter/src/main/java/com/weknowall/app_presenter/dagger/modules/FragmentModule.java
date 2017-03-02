package com.weknowall.app_presenter.dagger.modules;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 16-29
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }
}
