package com.kuenzWin.weplay.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

	public FlowLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		//获取当前flowlayout的测量模式和测量宽高
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		// 为了测量每个孩子需要制定每个孩子的规则
		int childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
				: widthMode;
		int childheightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
				: heightMode;

		for (int i = 0; i < getChildCount(); i++) {
			View child = this.getChildAt(i);
			// 测量每个孩子
			child.measure(childWidthMode, childheightMode);
		}

	}

}
