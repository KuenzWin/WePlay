package com.kuenzWin.weplay.fragment;

import android.os.Bundle;
import android.view.View;

import com.kuenzWin.weplay.base.BaseAppFragment;
import com.kuenzWin.weplay.holder.HomePictureHolder;
import com.kuenzWin.weplay.protocol.HomeProtocol;

public class HomeFragment extends BaseAppFragment {

	private HomeProtocol mHomeProtocol;

	public HomeFragment() {
		super();
		mHomeProtocol = new HomeProtocol();
		setProtocol(mHomeProtocol);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}

	@Override
	protected void addViewPager() {
		HomePictureHolder holder = new HomePictureHolder();
		holder.setData(mHomeProtocol.getPictures());
		View view = holder.getContentView();
		bl.addHeaderView(view);
	}
}
