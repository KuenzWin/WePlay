package com.kuenzWin.weplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.utils.BitmapHelper;
import com.kuenzWin.weplay.utils.UIUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

public class BaseListView extends ListView {

	public BaseListView(Context context) {
		this(context, null);
	}

	public BaseListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/**
	 * 对ListView进行一系列属性设置
	 */
	private void init() {
		// 设置点击时的颜色
		this.setSelector(R.drawable.nothing);
		// 设置拖拽时的颜色
		this.setCacheColorHint(0);
		// 设置分隔线的颜色
		this.setDivider(UIUtils.getDrawalbe(R.drawable.nothing));
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// 设置listview滑动的监听器
		// 第二个参数：慢慢滑动是否不加载图片 true为不加载，false为加载
		// 第三个参数：飞速滑动是否不加载图片 true为不加载，false为加载
		this.setOnScrollListener(new PauseOnScrollListener(BitmapHelper
				.getBitmapUtils(), false, true));
	}

}
