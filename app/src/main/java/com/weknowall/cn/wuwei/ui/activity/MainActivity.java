package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 11-24
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_mvp_test)
    Button mMvpTest;
    @BindView(R.id.main_swipe_delete)
    Button mSwipeDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_mvp_test, R.id.main_swipe_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_mvp_test:
                startActivity(GitUsersActivity.class);
                break;
            case R.id.main_swipe_delete:
                startActivity(SwipeDeleteRecyclerViewActivtiy.class);
                break;
        }
    }
}
