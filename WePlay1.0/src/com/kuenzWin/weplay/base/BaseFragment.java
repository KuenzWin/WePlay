package com.kuenzWin.weplay.base;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.utils.BitmapHelper;
import com.kuenzWin.weplay.utils.UIUtils;
import com.kuenzWin.weplay.utils.ViewUtils;
import com.kuenzWin.weplay.view.LoadingPage;
import com.kuenzWin.weplay.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;

public abstract class BaseFragment extends Fragment {

	protected Context mContext = UIUtils.getContext();
	protected LoadingPage mLoadingPage;
	protected BitmapUtils mBitmapUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mBitmapUtils = BitmapHelper.getBitmapUtils();
		mBitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);
		mBitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
		mBitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
		// 之前的frameLayout 已经记录了一个父控件了，父控件是之前的ViewPager
		if (mLoadingPage == null) {
			mLoadingPage = new LoadingPage(UIUtils.getContext()) {

				@Override
				protected LoadResult onLoad() {
					return BaseFragment.this.onLoad();
				}

				@Override
				protected View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}
			};

		} else {
			// 移除frameLayout之前的父控件
			ViewUtils.removeParent(mLoadingPage);
		}
		// 根据服务器的数据，切换状态
		return mLoadingPage;
	}

	public void show() {
		if (mLoadingPage != null)
			mLoadingPage.show();
	}

	/**
	 * 从服务器加载数据
	 * 
	 * @return
	 */
	protected abstract LoadResult onLoad();

	/**
	 * 创建数据请求成功的界面
	 * 
	 * @return
	 */
	protected abstract View createSuccessView();

	/**
	 * 校验数据
	 */
	public LoadResult checkData(List datas) {
		// 请求服务器失败
		if (datas == null)
			return LoadResult.error;

		if (datas.size() == 0)
			return LoadResult.empty;

		return LoadResult.success;

	}

}
