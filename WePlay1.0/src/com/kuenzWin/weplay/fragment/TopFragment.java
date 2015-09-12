package com.kuenzWin.weplay.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.base.BaseFragment;
import com.kuenzWin.weplay.protocol.TopProtocol;
import com.kuenzWin.weplay.utils.DrawableUtils;
import com.kuenzWin.weplay.utils.UIUtils;
import com.kuenzWin.weplay.view.LoadingPage.LoadResult;

public class TopFragment extends BaseFragment implements OnClickListener {

	private List<String> mDatas;

	@Override
	protected LoadResult onLoad() {
		mDatas = new TopProtocol().load(0);
		return checkData(mDatas);
	}

	@Override
	protected View createSuccessView() {
		ScrollView scrollView = new ScrollView(mContext);
		LinearLayout layout = new LinearLayout(mContext);
		layout.setBackgroundResource(R.drawable.list_item_bg);
		layout.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < mDatas.size(); i++) {
			TextView tv = new TextView(mContext);
			LinearLayout.LayoutParams lp = new LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			tv.setLayoutParams(lp);
			tv.setText(mDatas.get(i));
			Random random = new Random();
			// 随机颜色
			int color = Color.rgb(random.nextInt(200) + 20,
					random.nextInt(200) + 20, random.nextInt(200) + 20);
			// TextView背景为状态选择器的圆角图标
			tv.setBackgroundDrawable(DrawableUtils.createSelectorDrawable(
					DrawableUtils.createShape(0xffcecece),
					DrawableUtils.createShape(color)));
			// 单行
			tv.setSingleLine();
			tv.setTextColor(Color.WHITE);
			// 将内部文字记录起来
			tv.setTextSize(UIUtils.dip2px(14));
			// 设置内边距
			int paddingV = UIUtils.dip2px(4);
			int paddingH = UIUtils.dip2px(7);
			tv.setPadding(paddingV, paddingH, paddingV, paddingH);
			tv.setOnClickListener(this);
			layout.addView(tv);
		}
		scrollView.addView(layout);
		return scrollView;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(mContext, ((TextView) v).getText().toString(),
				Toast.LENGTH_SHORT).show();
	}
}
