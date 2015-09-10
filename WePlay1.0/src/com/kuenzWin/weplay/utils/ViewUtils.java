package com.kuenzWin.weplay.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
	public static void removeParent(View v) {
		// 先找到父控件 在通过父控件去移除子控件
		ViewParent parent = v.getParent();
		// 所有的控件 都有父控件 父控件一般情况下 就是ViewGoup
		if (parent instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) parent;
			group.removeView(v);
		}
	}
}
