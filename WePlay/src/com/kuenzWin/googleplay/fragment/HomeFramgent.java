package com.kuenzWin.googleplay.fragment;

import java.util.List;

import android.view.View;

import com.kuenzWin.googleplay.base.BaseFragment;
import com.kuenzWin.googleplay.base.BaseHolder;
import com.kuenzWin.googleplay.base.MyBaseAdapter;
import com.kuenzWin.googleplay.bean.AppInfo;
import com.kuenzWin.googleplay.holder.HomeHolder;
import com.kuenzWin.googleplay.protocol.HomeProtocol;
import com.kuenzWin.googleplay.utils.UIUtils;
import com.kuenzWin.googleplay.view.BaseListView;
import com.kuenzWin.googleplay.view.LoadingPage.LoadResult;

/**
 * @author 温坤哲
 * @date 2015-6-11
 */
public class HomeFramgent extends BaseFragment {

	private List<AppInfo> mDatas;

	@Override
	protected LoadResult load() {
		HomeProtocol protocol = new HomeProtocol();
		mDatas = protocol.load(0);
		LoadResult result = this.checkData(mDatas);
		return result;
	}

	@Override
	protected View createSucceedView() {
		BaseListView lv = new BaseListView(UIUtils.getContext());
		lv.setAdapter(new MyListAdapter(mDatas));
		return lv;
	}

	private class MyListAdapter extends MyBaseAdapter<AppInfo> {

		public MyListAdapter(List<AppInfo> mDatas) {
			super(mDatas);
		}

		@Override
		protected BaseHolder<AppInfo> getHolder() {
			return new HomeHolder();
		}

		@Override
		protected List onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			return protocol.load(mDatas.size());
		}

	}

}
