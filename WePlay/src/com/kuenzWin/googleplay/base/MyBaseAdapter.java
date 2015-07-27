package com.kuenzWin.googleplay.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kuenzWin.googleplay.holder.MoreHolder;
import com.kuenzWin.googleplay.manager.ThreadManager;
import com.kuenzWin.googleplay.utils.UIUtils;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	private List<T> mDatas;
	private BaseHolder holder;

	public MyBaseAdapter(List<T> mDatas) {
		setData(mDatas);
	}

	public void setData(List<T> mDatas) {
		this.mDatas = mDatas;

	}

	public List<T> getData() {
		return mDatas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (convertView != null) {
			holder = (BaseHolder) convertView.getTag();
		} else {
			if (type == MORE_VIEW_TYPE) {
				holder = getMoreHolder();
			} else {
				holder = getHolder();

			}
		}
		if (type == ITEM_VIEW_TYPE) {
			holder.setData(mDatas.get(position));
		}
		return holder.getRootView();
	}

	private BaseHolder getMoreHolder() {
		MoreHolder holder = new MoreHolder(this, hasMore());
		return holder;
	}

	public boolean hasMore() {
		return true;
	}

	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;
	}

	/**
	 * 加载更多的view类型
	 */
	private static final int MORE_VIEW_TYPE = 0;
	/**
	 * item的view类型
	 */
	private static final int ITEM_VIEW_TYPE = 1;

	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1)
			return MORE_VIEW_TYPE;
		return ITEM_VIEW_TYPE;
	}

	protected abstract BaseHolder getHolder();

	private boolean is_load = false;

	public void LoadMore() {
		if (!is_load) {
			is_load = true;
			ThreadManager.getLongPool().execute(new Runnable() {

				@Override
				public void run() {
					final List list = onLoadMore();
					UIUtils.runInMainThread(new Runnable() {

						@Override
						public void run() {
							if (list == null) {
								getMoreHolder().setData(MoreHolder.ERROR);
							} else if (list.size() < 20) {
								getMoreHolder().setData(MoreHolder.NO_MORE);
							} else {
								getMoreHolder().setData(MoreHolder.HAS_MORE);
							}

							if (null != list) {
								if (null != mDatas) {
									mDatas.addAll(list);
								} else {
									setData(list);
								}
							}
							notifyDataSetChanged();
							is_load = false;
						}

					});
				}
			});
		}
	}

	protected abstract List onLoadMore();

}
