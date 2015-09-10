package com.kuenzWin.weplay.base;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.kuenzWin.weplay.DetailActivity;
import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.domain.AppInfo;
import com.kuenzWin.weplay.protocol.AppProtocol;
import com.kuenzWin.weplay.protocol.GameProtocol;
import com.kuenzWin.weplay.protocol.HomeProtocol;
import com.kuenzWin.weplay.utils.UIUtils;
import com.kuenzWin.weplay.view.BaseListView;
import com.kuenzWin.weplay.view.LoadingPage.LoadResult;

/**
 * HomeFragment/AppFragment/GameFragment的基类
 * 
 * @author KuenzWin
 * @date 2015-9-7
 */
public class BaseAppFragment extends BaseFragment implements
		OnItemClickListener {

	protected List<AppInfo> mDatas;

	private BaseProtocol<List<AppInfo>> mBaseProtocol;

	protected BaseListView bl;

	protected void setProtocol(BaseProtocol<List<AppInfo>> baseProtocol) {
		this.mBaseProtocol = baseProtocol;
	}

	protected View createSuccessView() {
		bl = new BaseListView(UIUtils.getContext());
		BaseListAdapter adapter = new BaseListAdapter(UIUtils.getContext(),
				mDatas, R.layout.item_app) {
			@Override
			protected List<AppInfo> onLoadMore() {
				BaseProtocol<List<AppInfo>> bp = null;
				if (mBaseProtocol instanceof AppProtocol) {
					bp = new AppProtocol();
				} else if (mBaseProtocol instanceof GameProtocol) {
					bp = new GameProtocol();
				} else if (mBaseProtocol instanceof HomeProtocol) {
					bp = new HomeProtocol();
				}
				return bp.load(mDatas.size());
			}
		};
		// 添加头ViewPager
		addViewPager();
		bl.setAdapter(adapter);
		bl.setOnItemClickListener(this);
		return bl;
	}

	@Override
	protected LoadResult onLoad() {
		mDatas = mBaseProtocol.load(0);
		return checkData(mDatas);
	}

	protected void addViewPager() {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 因为有些ListView可能还有headView，所以对position进行矫正
		position = position - bl.getHeaderViewsCount();
		onInnerItemClick(position);
	}

	private void onInnerItemClick(int position) {
		UIUtils.startActivity(new Intent(UIUtils.getContext(),
				DetailActivity.class).putExtra("packageName",
				mDatas.get(position).getPackageName()));
	}

}
