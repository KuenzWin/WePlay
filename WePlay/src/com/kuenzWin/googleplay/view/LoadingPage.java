package com.kuenzWin.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.kuenzWin.googleplay.R;
import com.kuenzWin.googleplay.manager.ThreadManager;
import com.kuenzWin.googleplay.utils.UIUtils;

/**
 * 各个界面的内容部分，其存在是为了抽取每个页面的共同的部分， 如：加载数据的过程，数据之后的处理：加载错误，加载为空，加载成功的页面显示结果
 * 
 * @author 温坤哲
 * @date 2015-6-11
 */
public abstract class LoadingPage extends FrameLayout {
	// 加载默认的状态
	private static final int STATE_UNLOADED = 1;
	// 加载的状态
	private static final int STATE_LOADING = 2;
	// 加载失败的状态
	private static final int STATE_ERROR = 3;
	// 加载空的状态
	private static final int STATE_EMPTY = 4;
	// 加载成功的状态
	private static final int STATE_SUCCEED = 5;

	private View mLoadingView;// 转圈的view
	private View mErrorView;// 错误的view
	private View mEmptyView;// 空的view
	private View mSucceedView;// 成功的view

	private int mState;// 默认的状态

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// 初始化状态
		mState = STATE_UNLOADED;

		mLoadingView = createLoadingView();
		if (null != mLoadingView) {
			addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		mErrorView = createErrorView();
		if (null != mErrorView) {
			addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		mEmptyView = createEmptyView();
		if (null != mEmptyView) {
			addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		showSafePagerView();
	}

	private void showSafePagerView() {
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				showPagerView();
			}
		});
	}

	private void showPagerView() {
		if (null != mLoadingView) {
			mLoadingView.setVisibility(mState == STATE_UNLOADED
					|| mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (null != mErrorView) {
			mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (null != mEmptyView) {
			mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}

		if (mState == STATE_SUCCEED && mSucceedView == null) {
			mSucceedView = createLoadedView();
			addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		if (null != mSucceedView) {
			mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE
					: View.INVISIBLE);
		}
	}

	/**
	 * 显示界面，判断标志位以更新界面
	 */
	public void show() {
		if (mState == STATE_ERROR || mState == STATE_EMPTY) {
			mState = STATE_UNLOADED;
		}
		if (mState == STATE_UNLOADED) {
			mState = STATE_LOADING;
			TaskRunnable task = new TaskRunnable();
			ThreadManager.getLongPool().execute(task);
		}
		showSafePagerView();
	}

	protected View createLoadingView() {
		return UIUtils.inflate(R.layout.view_loading_page_loading);
	}

	protected View createEmptyView() {
		return UIUtils.inflate(R.layout.view_loading_page_empty);
	}

	protected View createErrorView() {
		View view = UIUtils.inflate(R.layout.view_loading_page_error);
		return view;
	}

	public abstract View createLoadedView();

	public abstract LoadResult load();

	class TaskRunnable implements Runnable {
		@Override
		public void run() {
			final LoadResult loadResult = load();
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					mState = loadResult.getValue();
					showPagerView();
				}
			});
		}
	}

	public enum LoadResult {
		ERROR(3), EMPTY(4), SUCCEED(5);
		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
