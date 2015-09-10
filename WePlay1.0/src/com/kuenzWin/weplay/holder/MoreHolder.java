package com.kuenzWin.weplay.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.adapter.CommonAdapter;
import com.kuenzWin.weplay.base.BaseListViewHolder;

public class MoreHolder extends BaseListViewHolder {

	private CommonAdapter adapter;

	/**
	 * 没有额外数据了
	 */
	public static final int HAS_NO_MORE = 0;
	/**
	 * 加载失败
	 */
	public static final int LOAD_ERROR = 1;
	/**
	 * 有额外数据
	 */
	public static final int HAS_MORE = 2;

	protected MoreHolder(Context context, ViewGroup parent, int position,
			CommonAdapter adapter) {
		super(context, parent, R.layout.load_more, position);
		this.adapter = adapter;
	}

	public static MoreHolder get(Context context, View convertView,
			ViewGroup parent, int position, CommonAdapter adapter) {
		if (null == convertView) {
			return new MoreHolder(context, parent, position, adapter);
		} else {
			MoreHolder holder = (MoreHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	@Override
	public View getConvertView() {
		// 返回View对象即加载更多条目显示时，连接服务器加载更多
		this.loadMore();
		return super.getConvertView();
	}

	/**
	 * 加载更多
	 */
	private void loadMore() {
		adapter.loadMore();
	}

	public void setMessageText(int flag) {
		switch (flag) {
		case HAS_NO_MORE:
			getView(R.id.rl_more_loading).setVisibility(View.INVISIBLE);
			getView(R.id.rl_more_loading).setVisibility(View.VISIBLE);
			getView(R.id.loading_pb).setVisibility(View.INVISIBLE);
			setText(R.id.loading_txt, "没有更多数据了");
			break;
		case LOAD_ERROR:
			getView(R.id.rl_more_loading).setVisibility(View.INVISIBLE);
			getView(R.id.rl_more_error).setVisibility(View.VISIBLE);
			break;
		}
	}

}
