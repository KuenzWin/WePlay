package com.kuenzWin.weplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 代码创建Drawable
 * 
 * @author KuenzWin
 * @date 2015-9-11
 */
public class DrawableUtils {

	/**
	 * 创建图片
	 * 
	 * @param color
	 *            颜色
	 * @return 图片Drawable
	 */
	public static GradientDrawable createShape(int color) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setCornerRadius(UIUtils.dip2px(5));
		drawable.setColor(color);
		return drawable;
	}

	/**
	 * 创建状态选择器
	 * 
	 * @param pressedDrawable
	 *            按下的图片
	 * @param normalDrawable
	 *            默认的图片
	 * @return 状态选择器
	 */
	public static StateListDrawable createSelectorDrawable(
			Drawable pressedDrawable, Drawable normalDrawable) {
		StateListDrawable drawable = new StateListDrawable();
		// 按下显示的图片
		drawable.addState(new int[] { android.R.attr.state_pressed },
				pressedDrawable);
		// 抬起显示的图片
		drawable.addState(new int[] {}, normalDrawable);
		return drawable;
	}
}
