package com.weknowall.cn.wuwei.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.ToolBar;
import com.weknowall.cn.wuwei.widget.plus.df.PlusSwipeDeleteRecyclerView;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-03-09
 * Time: 16-53
 */

public class SwipeDeleteRecyclerViewActivtiy extends BaseActivity {
    @BindView(R.id.swipe_delete_recycler_view)
    PlusSwipeDeleteRecyclerView mSwipeDeleteRecyclerView;
    @BindView(R.id.toolbar)
    ToolBar mToolbar;

    private SwipeDeleteAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_delete_recycler_view);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        mSwipeDeleteRecyclerView.initDefault();
        mSwipeDeleteRecyclerView.setOnPlusRefreshListener(() -> {
            //这里没有刷新操作，仅仅是让下拉刷新框收回
            mSwipeDeleteRecyclerView.notifySuccess();
        });
        mSwipeDeleteRecyclerView.setOnPlusLoadMoreListener(() -> {
            //处理加载更多的逻辑
            mSwipeDeleteRecyclerView.notifyFinish();
        });

        SwipeMenuRecyclerView mRecyclerView = (SwipeMenuRecyclerView) mSwipeDeleteRecyclerView.getRecycler();

        mRecyclerView.setAdapter(mAdapter = new SwipeDeleteAdapter());
        mRecyclerView.setSwipeMenuCreator(mMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("测试" + i);
        }
        mAdapter.setDatas(mList);
    }

    private OnSwipeMenuItemClickListener mMenuItemClickListener =new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();
            mList.remove(adapterPosition);
            mAdapter.notifyItemRemoved(adapterPosition);
            showToast(getString(R.string.remove_success));
        }
    };

    private SwipeMenuCreator mMenuCreator=new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.size_100);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                        .setBackgroundDrawable(R.drawable.draw_shape_rectangle_primary_solid)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    class SwipeDeleteAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

        private List<String> mDatas;

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_delete, parent, false);
        }

        @Override
        public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new ItemViewHolder(realContentView);
        }

        public void setDatas(List<String> datas){
            mDatas = datas;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            if (mDatas!=null){
                viewHolder.setData(mDatas.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.swipe_delete_title)
            TextView mTitle;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setData(String title){
                mTitle.setText(title);
            }
        }

    }

}
