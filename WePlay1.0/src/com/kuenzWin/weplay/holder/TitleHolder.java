package com.kuenzWin.weplay.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.base.BaseListViewHolder;

public class TitleHolder extends BaseListViewHolder {


	protected TitleHolder(Context context, ViewGroup parent, int position) {
		super(context, parent, R.layout.item_category_title, position);
	}

	@Override
	public View getConvertView() {
		return super.getConvertView();
	}

	public static TitleHolder get(Context context, View convertView,
			ViewGroup parent, int position) {
		if (null == convertView) {
			return new TitleHolder(context, parent, position);
		} else {
			TitleHolder holder = (TitleHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

}