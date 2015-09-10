package com.kuenzWin.weplay.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kuenzWin.weplay.base.BaseListViewHolder;
import com.kuenzWin.weplay.holder.MoreHolder;
import com.kuenzWin.weplay.manager.ThreadManager;
import com.kuenzWin.weplay.utils.UIUtils;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	protected int mlayoutId;

	public CommonAdapter(Context context, List<T> datas, int layoutId) {
		this.mContext = context;
		this.mDatas = datas;
		this.mlayoutId = layoutId;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mDatas.size() + 1;
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == mDatas.size())
			return TYPE_MORE;
		return TYPE_DATA;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	private static final int TYPE_DATA = 0;
	private static final int TYPE_MORE = 1;

	private MoreHolder mMoreHolder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseListViewHolder holder = null;
		if (getItemViewType(position) == TYPE_DATA) {
			holder = BaseListViewHolder.get(mContext, convertView, parent,
					mlayoutId, position);
			setData(holder, getItem(position));
		} else {
			if (mMoreHolder == null) {
				holder = MoreHolder.get(mContext, convertView, parent,
						position, this);
				mMoreHolder = (MoreHolder) holder;
			} else {
				holder = mMoreHolder;
			}
		}
		return holder.getConvertView();
	}

	public abstract void setData(BaseListViewHolder holder, T t);

	/**
	 * 加载更多
	 */
	public void loadMore() {
		ThreadManager.getInstance().getLongThreadPool()
				.executor(new Runnable() {

					@Override
					public void run() {
						// 在子线程中加载更多
						final List<T> newData = onLoadMore();

						// 在主线程更新数据
						UIUtils.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (newData == null) {
									// 连接服务器失败
									mMoreHolder
											.setMessageText(MoreHolder.LOAD_ERROR);
								} else if (newData.size() == 0) {
									// 没有更多的数据可加载了
									mMoreHolder
											.setMessageText(MoreHolder.HAS_NO_MORE);
								} else {
									mDatas.addAll(newData);
									notifyDataSetChanged();
								}
							}
						});

					}
				});
	}

	/**
	 * 请求服务器加载更多
	 * 
	 * @return 请求服务器后得到的数据
	 */
	protected abstract List<T> onLoadMore();

}
