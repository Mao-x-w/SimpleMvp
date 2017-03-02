package com.weknowall.cn.wuwei.widget.image.ninegrid;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

/**
 * User: laomao
 * Date: 2016-11-18
 * Time: 10-25
 */

public abstract class NineGridImageViewAdapter<T> {

    protected abstract void onDisplayImage(Context context, ImageView imageView, T t);

    protected void onItemImageClick(Context context, int index, List<T> list) {
    }

    protected ImageView generateImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
