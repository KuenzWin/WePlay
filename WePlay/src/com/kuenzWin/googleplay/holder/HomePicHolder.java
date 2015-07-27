package com.kuenzWin.googleplay.holder;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.kuenzWin.googleplay.R;
import com.kuenzWin.googleplay.base.BaseHolder;
import com.kuenzWin.googleplay.image.ImageLoader;
import com.kuenzWin.googleplay.utils.UIUtils;
import com.kuenzWin.googleplay.view.IndicatorView;

public class HomePicHolder extends BaseHolder<List<String>> implements OnPageChangeListener {

	private List<String> data;
	private ViewPager viewPager;
	private RelativeLayout.LayoutParams rl;
	private IndicatorView indicatorView;
	private AutoPlayTask autoPlayTask;

	@Override
	public void refreshView() {
		data = getData();
		viewPager.setCurrentItem(0);
		indicatorView.setCount(data.size());
		autoPlayTask.start();
	}

	@Override
	public View initView() {
		// 初始化头。由于使用相对布局方便放置可以跳动的点
		RelativeLayout mHeadView = new RelativeLayout(UIUtils.getContext());
		// 设置轮播图的宽和高
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT,
				UIUtils.getDimens(R.dimen.list_header_hight));

		mHeadView.setLayoutParams(params);
		// 初始化轮播图

		viewPager = new ViewPager(UIUtils.getContext());
		// 初始化轮播图的宽和高

		rl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		viewPager.setLayoutParams(rl);

		ViewPagerAdapter adapter = new ViewPagerAdapter();

		viewPager.setAdapter(adapter);
		// 初始化点

		indicatorView = new IndicatorView(UIUtils.getContext());

		rl = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 设置到屏幕的下边
		rl.addRule(mHeadView.ALIGN_PARENT_BOTTOM);
		rl.addRule(mHeadView.ALIGN_PARENT_RIGHT);
		// 设置点的背景图片
		indicatorView.setIndicatorDrawable(UIUtils
				.getDrawable(R.drawable.indicator));
		// 设置点的间距
		rl.setMargins(0, 0, 20, 20);
		indicatorView.setLayoutParams(rl);
		
		indicatorView.setSelection(0);

		autoPlayTask = new AutoPlayTask();
		
		viewPager.setOnPageChangeListener(this);

		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					autoPlayTask.stop();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					autoPlayTask.start();
				}

				return false;
			}
		});

		// 把点和轮播图添加到头里面去
		mHeadView.addView(viewPager);

		mHeadView.addView(indicatorView);

		return mHeadView;
	}

	private class AutoPlayTask implements Runnable {

		private int AUTO_PLAY_TIME = 1000;
		
		private boolean has_auto_play = false;
		
		@Override
		public void run() {
			if(has_auto_play){
				//has_auto_play = false;
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				UIUtils.postDelayed(this, AUTO_PLAY_TIME);
			}

		}

		public void stop() {
			has_auto_play = false;
			UIUtils.removeCallbacks(this);
			
		}

		public void start() {
			if(!has_auto_play){
				has_auto_play = true;
				UIUtils.removeCallbacks(this);
				UIUtils.postDelayed(this, AUTO_PLAY_TIME);
			}
			
		}

	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(UIUtils.getContext());
			int size = getData().size();
			ImageLoader.load(imageView, getData().get(position % size ));
			// 如果是从网络获取图片的话。必须增加这个属性
			imageView.setScaleType(ScaleType.FIT_XY);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		indicatorView.setSelection(position % getData().size());
		
	}

}
