package com.weknowall.cn.wuwei.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;


/**
 * User: Joy
 * Date: 2016/10/14
 * Time: 15:57
 */

public class GridItemDecorationProvider {

	public enum Orientation {
		Horizontal, Vertical
	}

	public static FlexibleDividerDecoration createHorizontalDivider(Context context, int size, int column, int color){
		return new HorizontalDividerItemDecoration.Builder(context).sizeProvider(new GridSizeProvider(Orientation.Horizontal, size, column))
				.color(color)
				.build();
	}

	public static FlexibleDividerDecoration createVerticalDivider(Context context, int size, int column, int color){
		return new VerticalDividerItemDecoration.Builder(context).sizeProvider(new GridSizeProvider(Orientation.Vertical, size, column))
				.color(color)
				.build();
	}

	public static FlexibleDividerDecoration createHorizontalDivider(Context context, int size, int column) {
		return createHorizontalDivider(context, size, column, Color.TRANSPARENT);
	}

	public static FlexibleDividerDecoration createVerticalDivider(Context context, int size, int column) {
		return createVerticalDivider(context, size, column, Color.TRANSPARENT);
	}

	private static class GridSizeProvider implements FlexibleDividerDecoration.SizeProvider {

		private final Orientation orientation;
		private final int size;
		private final int column;

		GridSizeProvider(Orientation orientation, int size, int column) {
			this.orientation = orientation;
			this.size = size;
			this.column = column;
		}

		@Override
		public int dividerSize(int position, RecyclerView parent) {
			return orientation == Orientation.Horizontal ? (position + 1) % column == 0 ? 0 : size : size;
		}
	}
}
