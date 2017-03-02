package com.weknowall.cn.wuwei.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class TabImageGroup extends TabViewBase<ImageView>{
	
	public TabImageGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean isInstanceOfChild(View v) {
		return v instanceof ImageView;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateChildStyle(int currentSelectId) {
	     for(int i = 0; i< getCustomChildCount(); i ++){
	    	 if(selectedDraw != null && unSelectedDraw != null){
	    		 getCustomChildViewList().get(i).setBackgroundDrawable( i == currentSelectId ? selectedDraw : unSelectedDraw );
	    	 }   		 
	     }
	}
}

