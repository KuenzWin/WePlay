package com.kuenzWin.weplay.fragment;

import com.kuenzWin.weplay.base.BaseAppFragment;
import com.kuenzWin.weplay.protocol.AppProtocol;

public class AppFragment extends BaseAppFragment {

	public AppFragment() {
		super();
		setProtocol(new AppProtocol());
	}

}
