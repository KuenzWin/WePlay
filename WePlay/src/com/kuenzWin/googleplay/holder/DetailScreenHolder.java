package com.kuenzWin.googleplay.holder;

import android.view.View;
import android.widget.ImageView;

import com.kuenzWin.googleplay.R;
import com.kuenzWin.googleplay.base.BaseHolder;
import com.kuenzWin.googleplay.bean.AppInfo;
import com.kuenzWin.googleplay.image.ImageLoader;
import com.kuenzWin.googleplay.utils.UIUtils;

public class DetailScreenHolder extends BaseHolder<AppInfo> {

	private ImageView[] screens;

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		
		for(int i = 0 ; i < 5 ;i++){
			ImageLoader.load(screens[i], appInfo.getScreen().get(i));
		}
		
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_screen);
		
		screens = new ImageView[5];
		
		screens[0] = (ImageView) view.findViewById(R.id.screen_1);
		screens[1] = (ImageView) view.findViewById(R.id.screen_2);
		screens[2] = (ImageView) view.findViewById(R.id.screen_3);
		screens[3] = (ImageView) view.findViewById(R.id.screen_4);
		screens[4] = (ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

}
