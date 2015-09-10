package com.kuenzWin.weplay.holder;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.base.BaseHolder;
import com.kuenzWin.weplay.domain.AppInfo;
import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class DetailSafeHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	@ViewInject(R.id.safe_layout)
	private RelativeLayout safe_layout;
	@ViewInject(R.id.safe_content)
	private LinearLayout safe_content;
	@ViewInject(R.id.safe_arrow)
	private ImageView safe_arrow;
	ImageView[] ivs;
	ImageView[] iv_des;
	TextView[] tv_des;
	LinearLayout[] des_layout;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.detail_safe);
		ViewUtils.inject(this, view);

		ivs = new ImageView[4]; // 初始化标题栏的图片
		ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
		ivs[3] = (ImageView) view.findViewById(R.id.iv_4);
		iv_des = new ImageView[4]; // 初始化每个条目描述的图片
		iv_des[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		iv_des[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		iv_des[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		iv_des[3] = (ImageView) view.findViewById(R.id.des_iv_4);
		tv_des = new TextView[4]; // 初始化每个条目描述的文本
		tv_des[0] = (TextView) view.findViewById(R.id.des_tv_1);
		tv_des[1] = (TextView) view.findViewById(R.id.des_tv_2);
		tv_des[2] = (TextView) view.findViewById(R.id.des_tv_3);
		tv_des[3] = (TextView) view.findViewById(R.id.des_tv_4);

		des_layout = new LinearLayout[4]; // 初始化条目线性布局
		des_layout[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		des_layout[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		des_layout[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		des_layout[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

		// 让safe_content隐藏
		LayoutParams layoutParams = safe_content.getLayoutParams();
		layoutParams.height = 0;
		safe_content.setLayoutParams(layoutParams);
		safe_arrow.setImageResource(R.drawable.arrow_down);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		safe_layout.setOnClickListener(this);

		List<String> safeUrl = data.getSafeUrl();
		List<String> safeDesUrl = data.getSafeDesUrl();
		List<String> safeDes = data.getSafeDes();
		List<Integer> safeDesColor = data.getSafeDesColor(); // 0 1 2 3
		for (int i = 0; i < 4; i++) {
			if (i < safeUrl.size() && i < safeDesUrl.size()
					&& i < safeDes.size() && i < safeDesColor.size()) {
				ivs[i].setVisibility(View.VISIBLE);
				des_layout[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(ivs[i], HttpHelper.URL + "image?name="
						+ safeUrl.get(i));
				bitmapUtils.display(iv_des[i], HttpHelper.URL + "image?name="
						+ safeDesUrl.get(i));
				tv_des[i].setText(safeDes.get(i));
				// 根据服务器数据显示不同的颜色
				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0); // 00 00 00
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				tv_des[i].setTextColor(color);

			} else {
				ivs[i].setVisibility(View.GONE);
				des_layout[i].setVisibility(View.GONE);
			}

		}

	}

	boolean flag = false;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.safe_layout) {
			int startHeight;
			int targetHeight;
			if (!flag) {
				// 展开的动画
				startHeight = 0;
				targetHeight = this.getMeasureHeight();
				flag = true;
				// safe_content.setVisibility(View.VISIBLE);
				// safe_content.getMeasuredHeight(); // 0
			} else {
				startHeight = this.getMeasureHeight();
				targetHeight = 0;

				flag = false;
				// safe_content.setVisibility(View.GONE);
			}
			// 值动画(利用复杂的数学算法让startHeight到targetHeight均匀变化)
			ValueAnimator va = ValueAnimator.ofInt(startHeight, targetHeight);
			va.addUpdateListener(new AnimatorUpdateListener() {
				RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) safe_content
						.getLayoutParams();

				@Override
				public void onAnimationUpdate(ValueAnimator valueAnimator) {
					int value = (Integer) valueAnimator.getAnimatedValue();
					lp.height = value;
					// 刷新界面
					safe_content.setLayoutParams(lp);
				}
			});
			va.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animator animator) {
					if (flag)
						safe_arrow.setImageResource(R.drawable.arrow_down);
					else
						safe_arrow.setImageResource(R.drawable.arrow_up);
				}

				@Override
				public void onAnimationCancel(Animator arg0) {
					// TODO Auto-generated method stub

				}
			});
			va.setDuration(500L);
			va.start();
		}
	}

	// onMeasure() 制定测量的规则
	// measure() 实际测量
	/**
	 * 获取控件实际的高度
	 */
	public int getMeasureHeight() {

		// 由于宽度是匹配父窗体的，无论是否发生动画都是如此，所以先给它测量出来
		int width = safe_content.getMeasuredWidth();
		// 高度是不固定的，让其包裹内容
		safe_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 参数1：大小，参数2：测量的模式
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.AT_MOST, 1000);// 我的高度 最大是1000
		// 测量规则 宽度是一个精确的值width, 高度最大是1000,以实际为准
		// 宽度是一个固定的值，高度是一个最大是一个值
		safe_content.measure(widthMeasureSpec, heightMeasureSpec);
		return safe_content.getMeasuredHeight();
	}
}
