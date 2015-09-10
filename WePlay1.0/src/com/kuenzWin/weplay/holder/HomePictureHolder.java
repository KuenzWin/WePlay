package com.kuenzWin.weplay.holder;

import java.util.LinkedList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.base.BaseHolder;
import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.utils.BitmapHelper;
import com.kuenzWin.weplay.utils.UIUtils;

public class HomePictureHolder extends BaseHolder<List<String>> {

	private ViewPager mViewPager;

	@Override
	public View initView() {
		mViewPager = new ViewPager(UIUtils.getContext()) {
			@Override
			public boolean dispatchTouchEvent(MotionEvent ev) {
				// 让这个显示图片的ViewPager的父ViewPager允许touch事件的分发
				// 即不拦截touch这个方法
				getParent().requestDisallowInterceptTouchEvent(true);
				return super.dispatchTouchEvent(ev);
			}

			@Override
			public boolean onTouchEvent(MotionEvent event) {
				switch (event.getAction()) {
				// 利用case穿透，当手指按下或滑动时结束自动轮询图片
				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_DOWN:
					task.stop();
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					task.start();
					break;
				}
				return super.onTouchEvent(event);
			}
		};
		mViewPager.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, UIUtils
						.getDimens(R.dimen.home_picture_height)));
		return mViewPager;
	}

	@Override
	public void refreshView(List<String> data) {
		this.data = data;
		mViewPager.setAdapter(new HomePictureAdapter());
		mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
		task = new AutoRoundRunnable();
		task.start();
	}

	private class HomePictureAdapter extends PagerAdapter {

		private LinkedList<ImageView> convertViews = new LinkedList<ImageView>();

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView iv = (ImageView) object;
			convertViews.add(iv);
			container.removeView(iv);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int index = position % data.size();
			ImageView iv = null;
			if (convertViews.size() > 0) {
				iv = convertViews.remove(0);
			} else {
				iv = new ImageView(UIUtils.getContext());
			}
			BitmapHelper.getBitmapUtils().display(iv,
					HttpHelper.URL + "image?name=" + data.get(index));
			container.addView(iv);
			return iv;
		}

	}

	private boolean isRound;

	public AutoRoundRunnable task;

	/**
	 * 自动轮询图片任务
	 * 
	 * @author KuenzWin
	 * @date 2015-9-8
	 */
	private class AutoRoundRunnable implements Runnable {

		@Override
		public void run() {
			if (isRound) {
				// 取消之前的
				UIUtils.cancel(this);
				int cur = mViewPager.getCurrentItem();
				mViewPager.setCurrentItem(++cur);
				// 递归调用这个方法
				UIUtils.postDelayed(this, 3 * 1000);
			}
		}

		/**
		 * 开始自动轮询图片任务
		 */
		public void start() {
			if (!isRound) {
				UIUtils.cancel(this);
				isRound = true;
				UIUtils.postDelayed(this, 3 * 1000);
			}
		}

		/**
		 * 结束自动轮询图片任务
		 */
		public void stop() {
			if (isRound) {
				isRound = false;
				UIUtils.cancel(this);
			}
		}

	}

}
