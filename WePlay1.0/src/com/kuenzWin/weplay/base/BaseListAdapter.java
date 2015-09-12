package com.kuenzWin.weplay.base;

import java.util.List;

import android.content.Context;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.adapter.CommonAdapter;
import com.kuenzWin.weplay.domain.AppInfo;
import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.utils.BitmapHelper;
import com.kuenzWin.weplay.utils.UIUtils;

public abstract class BaseListAdapter extends CommonAdapter<AppInfo> {

	public BaseListAdapter(Context context, List<AppInfo> datas, int layoutId) {
		super(context, datas, layoutId, true);
	}

	@Override
	public void setData(BaseListViewHolder holder, AppInfo info) {
		ImageView item_icon = (ImageView) holder.getView(R.id.item_icon);
		BitmapHelper.getBitmapUtils().display(item_icon,
				HttpHelper.URL + "image?name=" + info.getIconUrl());
		((TextView) holder.getView(R.id.item_title)).setText(info.getName());
		((RatingBar) holder.getView(R.id.item_rating)).setRating(info
				.getStars());
		((TextView) holder.getView(R.id.item_size)).setText(Formatter
				.formatFileSize(UIUtils.getContext(), info.getSize()));
		((TextView) holder.getView(R.id.item_bottom)).setText(info.getDes());
	}

}