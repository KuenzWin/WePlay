package com.kuenzWin.googleplay.holder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuenzWin.googleplay.R;
import com.kuenzWin.googleplay.base.BaseHolder;
import com.kuenzWin.googleplay.base.MyBaseAdapter;
import com.kuenzWin.googleplay.utils.UIUtils;

public class MoreHolder extends BaseHolder<Integer> {

	private MyBaseAdapter mBaseAdapter;

	/**
	 * 有更多的数据
	 */
	public static final int HAS_MORE = 1;

	/**
	 * 没有更多的数据
	 */
	public static final int NO_MORE = 0;

	/**
	 * 加载失败
	 */
	public static final int ERROR = -1;

	private RelativeLayout rl_more_loading;
	private ProgressBar loading_pb;
	private RelativeLayout rl_more_error;

	public MoreHolder(MyBaseAdapter mBaseAdapter, boolean hasMore) {
		this.mBaseAdapter = mBaseAdapter;
		setData(hasMore == true ? HAS_MORE : NO_MORE);
	}

	@Override
	public void refreshView() {
		Integer data = getData();
		rl_more_loading.setVisibility(data == HAS_MORE ? View.VISIBLE
				: View.INVISIBLE);
		rl_more_error.setVisibility(data == ERROR ? View.VISIBLE
				: View.INVISIBLE);
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_more_loading);

		rl_more_loading = (RelativeLayout) view
				.findViewById(R.id.rl_more_loading);
		rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);

		return view;
	}

	// 当出现了这个“加载更多的布局”的时候就进行加载
	@Override
	public View getRootView() {
		loadMore();
		return super.getRootView();
	}

	private void loadMore() {
		mBaseAdapter.LoadMore();
	}

}
