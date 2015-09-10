package com.kuenzWin.weplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.manager.ThreadManager;
import com.kuenzWin.weplay.utils.UIUtils;

/***
 * 创建了自定义帧布局 把baseFragment 一部分代码 抽取到这个类中
 * 
 * @author itcast
 * 
 */
public abstract class LoadingPage extends FrameLayout {

	public static final int STATE_UNKOWN = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public int state = STATE_UNKOWN;

	private View loadingView;// 加载中的界面
	private View errorView;// 错误界面
	private View emptyView;// 空界面
	private View successView;// 加载成功的界面

	public LoadingPage(Context context) {
		this(context, null);
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs, 0);

	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		loadingView = createLoadingView(); // 创建了加载中的界面
		if (loadingView != null) {
			addChildView(loadingView);
		}
		errorView = createErrorView(); // 加载错误界面
		if (errorView != null) {
			addChildView(errorView);
		}
		emptyView = createEmptyView(); // 加载空的界面
		if (emptyView != null) {
			addChildView(emptyView);
		}
		showPage();// 根据不同的状态显示不同的界面
	}

	private void addChildView(View view) {
		this.addView(view, new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	// 根据不同的状态显示不同的界面
	private void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (state == STATE_SUCCESS) {
			if (successView == null) {
				successView = createSuccessView();
				addChildView(successView);
			}
			successView.setVisibility(View.VISIBLE);
		} else {
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}

	/* 创建了空的界面 */
	private View createEmptyView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_empty,
				null);
		return view;
	}

	/* 创建了错误界面 */
	private View createErrorView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_error,
				null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	/* 创建加载中的界面 */
	private View createLoadingView() {
		View view = View.inflate(UIUtils.getContext(),
				R.layout.loadpage_loading, null);
		return view;
	}

	public enum LoadResult {
		error(2), empty(3), success(4);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	// 根据服务器的数据 切换状态
	public void show() {
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}
		// 请求服务器 获取服务器上数据 进行判断
		// 请求服务器 返回一个结果
		ThreadManager.getInstance().getLongThreadPool()
				.executor(new Runnable() {

					@Override
					public void run() {
						final LoadResult result = onLoad();
						UIUtils.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (result != null) {
									state = result.getValue();
									showPage(); // 状态改变了,重新判断当前应该显示哪个界面
								}
							}
						});
					}
				});

		showPage();

	}

	/***
	 * 创建成功的界面
	 * 
	 * @return
	 */
	protected abstract View createSuccessView();

	/**
	 * 请求服务器
	 * 
	 * @return
	 */
	protected abstract LoadResult onLoad();
}
