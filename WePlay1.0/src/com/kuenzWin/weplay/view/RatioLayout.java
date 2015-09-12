package com.kuenzWin.weplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {
	// 按照宽高比例去显示
	private float ratio = 2.43f; // 比例值

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public RatioLayout(Context context) {
		this(context, null);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		float ratio = attrs.getAttributeFloatValue(
				"http://schemas.android.com/apk/res/com.itheima.googleplay",
				"ratio", 2.43f);
		setRatio(ratio);
	}

	// 测量当前布局,修改测量规则
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 获取宽度模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// 得到宽度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		// 去除左右内边距，得到真实的宽度
		widthSize = widthSize - this.getPaddingLeft() - this.getPaddingRight();

		// 获取宽度模式
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		// 得到高度
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		// 去除上下内边距，得到真实的高度
		heightSize = heightSize - this.getPaddingTop()
				- this.getPaddingBottom();

		// 哪个是精确值，就以哪个为基准进行按比例设置
		if (widthMode == MeasureSpec.EXACTLY
				&& heightMode != MeasureSpec.EXACTLY) {
			// 对高度进行根据宽度的比例设置，+0.5是为了四舍五入
			heightSize = (int) (widthSize / ratio + 0.5);
		} else if (widthMode != MeasureSpec.EXACTLY
				&& heightMode == MeasureSpec.EXACTLY) {
			// 由于高度是精确的值 ,宽度随着高度的变化而变化
			widthSize = (int) ((heightSize * ratio) + 0.5f);
		}

		// 重新指定测量规则
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(
				widthSize + this.getPaddingLeft() + this.getPaddingRight(),
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(
				heightSize + this.getPaddingTop() + this.getPaddingBottom(),
				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
