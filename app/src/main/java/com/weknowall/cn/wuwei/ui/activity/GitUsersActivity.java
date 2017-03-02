package com.weknowall.cn.wuwei.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weknowall.app_presenter.entity.general.GitUser;
import com.weknowall.app_presenter.presenter.general.GitUsersPresenter;
import com.weknowall.app_presenter.view.IGitUsersView;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.dagger.components.DaggerGeneralComponent;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.ToolBar;
import com.weknowall.cn.wuwei.widget.image.CircleImageView;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.AdapterPlus;
import com.weknowall.cn.wuwei.widget.recyclerview.adapter.ViewHolderPlus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-45
 */

public class GitUsersActivity extends BaseActivity implements IGitUsersView {

    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.git_users_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    GitUsersPresenter mPresenter;

    GitUsersAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_users);
        ButterKnife.bind(this);

        // 初始化网络请求
        DaggerGeneralComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build().inject(this);
        mPresenter.setView(this);

        // 初始化recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new GitUsersAdapter(getContext()));

        // 发起网络请求
        mPresenter.initialize();
    }

    /**
     * 网络请求返回数据
     * @param users
     */
    @Override
    public void onGetGitUsers(List<GitUser> users) {
        // 通过返回数据更新adapter
        mAdapter.clear();
        mAdapter.insertRange(users, false);
    }

    /**
     * RecylcerView对应Adapter
     */
    class GitUsersAdapter extends AdapterPlus<GitUser> {

        public GitUsersAdapter(Context context) {
            super(context);
        }

        @Override
        public ViewHolderPlus<GitUser> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
            return new ItemViewHolder(inflater.inflate(R.layout.item_git_users, parent, false));
        }

        class ItemViewHolder extends ViewHolderPlus<GitUser> {

            @BindView(R.id.git_users_avator)
            CircleImageView mAvator;
            @BindView(R.id.git_users_name)
            TextView mName;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onBinding(int position, GitUser gitUser) {
                mAvator.setImageUrl(gitUser.getAvatar());
                mName.setText(gitUser.getName());
            }
        }

    }
}
