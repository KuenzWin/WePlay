package com.kuenzWin.googleplay.base;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuenzWin.googleplay.utils.UIUtils;
import com.kuenzWin.googleplay.view.LoadingPage;
import com.kuenzWin.googleplay.view.LoadingPage.LoadResult;

public abstract class BaseFragment extends Fragment {

	private LoadingPage mContentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mContentView == null) {
			mContentView = new LoadingPage(UIUtils.getContext()) {

				/**
				 * 加载数据
				 */
				@Override
				public LoadResult load() {
					return BaseFragment.this.load();
				}

				/**
				 * 创建View
				 */
				@Override
				public View createLoadedView() {
					return BaseFragment.this.createSucceedView();
				}
			};
		}
		return mContentView;
	}

	/**
	 * 展示具体的页面
	 */
	public void show() {
		if (null != mContentView) {
			mContentView.show();
		}
	}

	/**
	 * 加载方法，写程序的加载过程
	 * 
	 * @return 加载是否成功的标志位
	 */
	protected abstract LoadResult load();

	/**
	 * 创建成功加载的界面
	 * 
	 * @return 成功加载的界面
	 */
	protected abstract View createSucceedView();

	/**
	 * 检查数据的正确性
	 * 
	 * @param mData
	 *            数据
	 * @return 数据加载的三种结果:1、加载错误 2、数据是否为空 3、数据加载成功
	 */
	protected LoadResult checkData(Object mData) {
		if (mData == null)
			return LoadResult.ERROR;
		if (mData instanceof List) {
			if (((List) mData).size() == 0)
				return LoadResult.EMPTY;
		}
		return LoadResult.SUCCEED;
	}

}
