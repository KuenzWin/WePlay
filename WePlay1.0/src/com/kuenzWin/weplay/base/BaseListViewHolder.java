package com.kuenzWin.weplay.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseListViewHolder {

	private SparseArray<View> mViews;
	protected int mPosition;
	protected View mConvertView;
	
	protected BaseListViewHolder(Context context, ViewGroup parent, int position) {
		this.mViews = new SparseArray<View>();
		this.mPosition = position;
	}

	protected BaseListViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this(context, parent, position);
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId,
				parent, false);
		this.mConvertView.setTag(this);
	}

	protected BaseListViewHolder(Context context, ViewGroup parent, View view,
			int position) {
		this(context, parent, position);
		this.mConvertView = view;
		this.mConvertView.setTag(this);
	}

	public static BaseListViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (null == convertView) {
			return new BaseListViewHolder(context, parent, layoutId, position);
		} else {
			BaseListViewHolder holder = (BaseListViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	/**
	 * 获取view
	 * 
	 * @param viewId
	 * @return 获取view对象
	 */
	public <T extends View> T getView(int viewId) {

		View view = mViews.get(viewId);

		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}

		return (T) view;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public BaseListViewHolder setText(int viewId, String text) {
		TextView tv = (TextView) mConvertView.findViewById(viewId);
		tv.setText(text);
		return this;
	}

}
