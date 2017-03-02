package com.weknowall.cn.wuwei.widget.image.ninegrid;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: laomao
 * Date: 2016-11-18
 * Time: 10-07
 */

public class NineGridImageView<T> extends ViewGroup {

    public final static int STYLE_GRID = 0;     // 宫格布局
    public final static int STYLE_FILL = 1;     // 全填充布局

    private int mRowCount;       // 行数
    private int mColumnCount;    // 列数

    private int mMaxSize;        // 最大图片数
    private int mShowStyle;     // 显示风格
    private int mGap;           // 宫格间距
    private int mSingleImageDisplayWidth; // 单张图片时的真实展示宽度
    private int mGridSize;   // 宫格大小,即图片大小
    private int mSingleImageHeight;       //图片的真实高宽
    private int mSingleImageWidth;       //图片的真实宽度
    private float scale=1;         // 图片的宽高比例  默认为1

    private List<ImageView> mImageViewList = new ArrayList<>();
    private List<T> mImgDataList;

    private NineGridImageViewAdapter<T> mAdapter;

    public NineGridImageView(Context context) {
        this(context, null);
    }

    public NineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.weknowall.cn.wuwei.R.styleable.NineGridImageView);
        this.mGap = (int) typedArray.getDimension(com.weknowall.cn.wuwei.R.styleable.NineGridImageView_imgGap, 0);
        this.mSingleImageDisplayWidth = typedArray.getDimensionPixelSize(com.weknowall.cn.wuwei.R.styleable.NineGridImageView_singleImageWidth, -1);
        this.mSingleImageHeight = typedArray.getDimensionPixelSize(com.weknowall.cn.wuwei.R.styleable.NineGridImageView_singleImageHeight, -1);
        this.mShowStyle = typedArray.getInt(com.weknowall.cn.wuwei.R.styleable.NineGridImageView_showStyle, STYLE_GRID);
        this.mMaxSize = typedArray.getInt(com.weknowall.cn.wuwei.R.styleable.NineGridImageView_maxSize, 9);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImgDataList != null && mImgDataList.size() > 0) {
            if (mImgDataList.size() == 1 && mSingleImageDisplayWidth != -1) {
                mGridSize = mSingleImageDisplayWidth > totalWidth ? totalWidth : mSingleImageDisplayWidth;
                scale= (float) ((1.0*mSingleImageHeight)/(1.0*mSingleImageWidth));
                if (scale<=0)
                    scale=1;
            } else {
                mImageViewList.get(0).setScaleType(ImageView.ScaleType.CENTER_CROP);
                mGridSize = (totalWidth - mGap * (mColumnCount - 1)) / mColumnCount;
                scale=1;
            }
            height = (int)(mGridSize*scale * mRowCount) + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        } else {
            height = width;
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenView();
    }

    /**
     * 布局 ImageView
     */
    private void layoutChildrenView() {
        if (mImgDataList == null) {
            return;
        }
        int childrenCount = mImgDataList.size();
        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            if (mAdapter != null) {
                mAdapter.onDisplayImage(getContext(), childrenView, mImgDataList.get(i));
            }
            int rowNum = i / mColumnCount;
            int columnNum = i % mColumnCount;
            int left = (mGridSize + mGap) * columnNum + getPaddingLeft();
            int top = ((int)(mGridSize*scale) + mGap) * rowNum + getPaddingTop();
            int right = left + mGridSize;
            int bottom = top + (int)(mGridSize*scale);

            childrenView.layout(left, top, right, bottom);
        }
    }

    /**
     * 设置图片数据
     *
     * @param lists 图片数据集合
     */
    public void setImagesData(List lists) {
        if (lists == null || lists.isEmpty()) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }

        if (mMaxSize > 0 && lists.size() > mMaxSize) {
            lists = lists.subList(0, mMaxSize);
        }

        int[] gridParam = calculateGridParam(lists.size(), mShowStyle);
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];
        if (mImgDataList == null) {
            int i = 0;
            while (i < lists.size()) {
                ImageView iv = getImageView(i);
                if (iv == null) {
                    return;
                }
                addView(iv, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldViewCount = mImgDataList.size();
            int newViewCount = lists.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = oldViewCount; i < newViewCount; i++) {
                    ImageView iv = getImageView(i);
                    if (iv == null) {
                        return;
                    }
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
        mImgDataList = lists;
        requestLayout();
    }

    /**
     * 获得 ImageView
     * 保证了 ImageView 的重用
     *
     * @param position 位置
     */
    private ImageView getImageView(final int position) {
        if (position < mImageViewList.size()) {
            return mImageViewList.get(position);
        } else {
            if (mAdapter != null) {
                ImageView imageView = mAdapter.generateImageView(getContext());
                mImageViewList.add(imageView);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.onItemImageClick(getContext(), position, mImgDataList);
                    }
                });
                return imageView;
            } else {
                Log.e("NineGirdImageView", "Your must set a NineGridImageViewAdapter for NineGirdImageView");
                return null;
            }
        }
    }

    /**
     * 设置 宫格参数
     *
     * @param imagesSize 图片数量
     * @param showStyle  显示风格
     * @return 宫格参数 gridParam[0] 宫格行数 gridParam[1] 宫格列数
     */
    protected static int[] calculateGridParam(int imagesSize, int showStyle) {
        int[] gridParam = new int[2];
        switch (showStyle) {
            case STYLE_FILL:
                if (imagesSize < 3) {
                    gridParam[0] = 1;
                    gridParam[1] = imagesSize;
                } else if (imagesSize <= 4) {
                    gridParam[0] = 2;
                    gridParam[1] = 2;
                } else {
                    gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                    gridParam[1] = 3;
                }
                break;
            default:
            case STYLE_GRID:
                gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                gridParam[1] = 3;
        }
        return gridParam;
    }

    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(NineGridImageViewAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * 设置宫格间距
     *
     * @param gap 宫格间距 px
     */
    public void setGap(int gap) {
        mGap = gap;
    }

    /**
     * 设置显示风格
     *
     * @param showStyle 显示风格
     */
    public void setShowStyle(int showStyle) {
        mShowStyle = showStyle;
    }

    /**
     * 设置只有一张图片时的尺寸大小
     *
     * @param singleImageWidth 单张图片的尺寸大小
     */
    public void setSingleImageWidth(int singleImageWidth) {
        mSingleImageWidth = singleImageWidth;
    }

    /**
     * 设置最大图片数
     *
     * @param maxSize 最大图片数
     */
    public void setMaxSize(int maxSize) {
        mMaxSize = maxSize;
    }

    public void setSingleImageHeight(int singleImageHeight) {
        mSingleImageHeight = singleImageHeight;
    }

    public void setSingleImageWidthAndHeight(int width,int height,int displayWidth){
        this.mSingleImageWidth=width;
        this.mSingleImageHeight=height;
        this.mSingleImageDisplayWidth=displayWidth;
    }

    public void setSingleImageWidthAndHeight(int width,int height){
        setSingleImageWidthAndHeight(width,height,mSingleImageDisplayWidth);
    }

}