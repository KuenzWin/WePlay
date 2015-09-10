package com.kuenzWin.weplay.fragment;

import com.kuenzWin.weplay.base.BaseAppFragment;
import com.kuenzWin.weplay.protocol.GameProtocol;

public class GameFragment extends BaseAppFragment {

	public GameFragment() {
		super();
		setProtocol(new GameProtocol());
	}

}
