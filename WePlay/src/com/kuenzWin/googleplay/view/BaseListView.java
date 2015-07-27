package com.kuenzWin.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.kuenzWin.googleplay.R;
import com.kuenzWin.googleplay.utils.UIUtils;

public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BaseListView(Context context) {
		super(context);
		setDivider(UIUtils.getDrawable(R.drawable.nothing));
		setCacheColorHint(UIUtils.getColor(R.color.bg_page));
		setSelector(UIUtils.getDrawable(R.drawable.nothing));
	}

}
