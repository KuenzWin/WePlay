package com.kuenzWin.weplay;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.kuenzWin.weplay.base.BaseActivity;
import com.kuenzWin.weplay.domain.AppInfo;
import com.kuenzWin.weplay.holder.DetailBottomHolder;
import com.kuenzWin.weplay.holder.DetailDesHolder;
import com.kuenzWin.weplay.holder.DetailInfoHolder;
import com.kuenzWin.weplay.holder.DetailSafeHolder;
import com.kuenzWin.weplay.holder.DetailScreenHolder;
import com.kuenzWin.weplay.protocol.DetailProtocol;
import com.kuenzWin.weplay.utils.LogUtils;
import com.kuenzWin.weplay.utils.UIUtils;
import com.kuenzWin.weplay.view.LoadingPage;
import com.kuenzWin.weplay.view.LoadingPage.LoadResult;

public class DetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo data;

	@Override
	protected void initView() {
		LoadingPage mLoadingPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			protected LoadResult onLoad() {

				return DetailActivity.this.onLoad();
			}

			@Override
			protected View createSuccessView() {
				return DetailActivity.this.createSuccessView();
			}
		};
		mLoadingPage.show();
		setContentView(mLoadingPage);
	}

	private FrameLayout bottom_layout, detail_info, detail_safe, detail_des;
	private HorizontalScrollView detail_screen;
	private DetailInfoHolder detailInfoHolder;
	private DetailScreenHolder screenHolder;
	private DetailSafeHolder safeHolder;
	private DetailDesHolder desHolder;
	private DetailBottomHolder bottomHolder;

	protected View createSuccessView() {
		View view = UIUtils.inflate(R.layout.activity_detail);
		// 添加信息区域
		bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
		bottomHolder = new DetailBottomHolder();
		bottomHolder.setData(data);
		bottom_layout.addView(bottomHolder.getContentView());

		// 操作 应用程序信息
		detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		detailInfoHolder = new DetailInfoHolder();
		detailInfoHolder.setData(data);
		detail_info.addView(detailInfoHolder.getContentView());
		detail_info.setVisibility(View.GONE);

		// 安全标记
		detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		safeHolder = new DetailSafeHolder();
		safeHolder.setData(data);
		detail_safe.addView(safeHolder.getContentView());

		detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
		desHolder = new DetailDesHolder();
		desHolder.setData(data);
		detail_des.addView(desHolder.getContentView());
		// 中间5张图片
		detail_screen = (HorizontalScrollView) view
				.findViewById(R.id.detail_screen);
		screenHolder = new DetailScreenHolder();
		screenHolder.setData(data);
		detail_screen.addView(screenHolder.getContentView());

		return view;
	}

	protected LoadResult onLoad() {
		DetailProtocol protocol = new DetailProtocol(packageName);
		data = protocol.load(0);
		if (data == null) {
			return LoadResult.error;
		}
		return LoadResult.success;
	}

	@Override
	protected void init() {
		Intent intent = this.getIntent();
		packageName = intent.getStringExtra("packageName");
	}

}
