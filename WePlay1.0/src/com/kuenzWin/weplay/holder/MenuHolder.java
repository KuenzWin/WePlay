package com.kuenzWin.weplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.base.BaseActivity;
import com.kuenzWin.weplay.base.BaseHolder;
import com.kuenzWin.weplay.domain.UserInfo;
import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.manager.ThreadManager;
import com.kuenzWin.weplay.protocol.UserProtocol;
import com.kuenzWin.weplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MenuHolder extends BaseHolder<UserInfo> {

	@ViewInject(R.id.photo_layout)
	private RelativeLayout photo_layout;
	@ViewInject(R.id.image_photo)
	private ImageView image_photo;
	@ViewInject(R.id.user_name)
	private TextView user_name;
	@ViewInject(R.id.user_email)
	private TextView user_email;
	@ViewInject(R.id.exit_layout)
	private RelativeLayout exit_layout;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.menu_holder);
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void refreshView(UserInfo data) {
		user_name.setText(data.getName());
		user_email.setText(data.getEmail());
		String url = data.getUrl();
		bitmapUtils.display(image_photo, HttpHelper.URL + "image?name=" + url);
	}

	@OnClick({ R.id.photo_layout, R.id.exit_layout })
	public void load(View view) {
		switch (view.getId()) {
		case R.id.photo_layout:
			ThreadManager.getInstance().getLongThreadPool()
					.executor(new Runnable() {

						@Override
						public void run() {
							UserProtocol protocol = new UserProtocol();
							final UserInfo info = protocol.load(0);
							UIUtils.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									setData(info); // 当调用该方法的时候 就会调用refreshView
								}
							});
						}
					});
			break;
		case R.id.exit_layout:
			BaseActivity.killAll();
			break;
		}
	}

}
