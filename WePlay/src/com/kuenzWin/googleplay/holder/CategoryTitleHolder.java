package com.kuenzWin.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.kuenzWin.googleplay.R;
import com.kuenzWin.googleplay.base.BaseHolder;
import com.kuenzWin.googleplay.bean.CategoryInfo;
import com.kuenzWin.googleplay.utils.UIUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tv_title;

	@Override
	public void refreshView() {
		CategoryInfo categoryInfo = getData();
		tv_title.setText(categoryInfo.getTitle());
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item_title);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

}
