package com.kuenzWin.weplay;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kuenzWin.weplay.base.BaseActivity;
import com.kuenzWin.weplay.base.BaseFragment;
import com.kuenzWin.weplay.factory.FragmentFactoty;
import com.kuenzWin.weplay.holder.MenuHolder;
import com.kuenzWin.weplay.utils.UIUtils;

/**
 * 
 * @author KuenzWin
 * @date 2015-9-1
 */
public class MainActivity extends BaseActivity implements OnQueryTextListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private ViewPager mViewPager;
	// private ImageButton mib;
	private PagerTabStrip mPagerTabStrip;
	// private TabPageIndicator mIndicator;

	private FrameLayout fl_menu;

	/**
	 * 标签名字
	 */
	private String[] tabNames;

	@Override
	protected void init() {
		tabNames = UIUtils.getStringArray(R.array.tab_names);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);

		mViewPager = (ViewPager) this.findViewById(R.id.vp);
		// mib = (ImageButton) this.findViewById(R.id.ib_news_menu_next_tab);
		// mIndicator = (TabPageIndicator) this.findViewById(R.id.tpi);
		// 设置标签下划线颜色
		mPagerTabStrip = (PagerTabStrip) this
				.findViewById(R.id.pager_tab_strip);
		mPagerTabStrip.setTabIndicatorColorResource(R.color.indicatorcolor);
		mPagerTabStrip.setBackgroundColor(Color.WHITE);
		mPagerTabStrip.setTextColor(Color.BLACK);

		mViewPager.setAdapter(new MyPagerAdapter(this
				.getSupportFragmentManager()));
		// mIndicator.setViewPager(mViewPager);
		// 给ViewPager注册一个监听器，使得每次切换界面都可以更新fragment的数据
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						// mIndicator.setCurrentItem(position);
						BaseFragment bf = FragmentFactoty
								.createFragment(position);
						bf.show();
					}
				});
		// mib.setOnClickListener(this);
		fl_menu = (FrameLayout) this.findViewById(R.id.fl_menu);
		MenuHolder menuHolder = new MenuHolder();
		fl_menu.addView(menuHolder.initView());
	}

	@Override
	protected void initActionBar() {
		super.initActionBar();
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.dl);
		// 1）显示Navigation Drawer的 Activity对象
		// 2） DrawerLayout 对象
		// 3）一个用来指示Navigation Drawer的 drawable资源
		// 4）一个用来描述打开Navigation Drawer的文本(用于支持可访问性)。
		// 5）一个用来描述关闭Navigation Drawer的文本(用于支持可访问性).
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_am, R.string.open_drawer,
				R.string.close_drawer) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// 将DrawerLayout与ActionBarDrawerToggle联系到一起
		mDrawerToggle.syncState();
	}

	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentFactoty.createFragment(position);
		}

		@Override
		public int getCount() {
			return tabNames.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabNames[position];
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		// 如果android运行环境大于10，即在3.0及以上的版本，可运行下列代码
		if (android.os.Build.VERSION.SDK_INT > 10) {
			SearchView sv = (SearchView) menu.findItem(R.id.action_search)
					.getActionView();
			sv.setOnQueryTextListener(this);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			openSearch();
			return true;
		}
		// 将menu菜单键点击事件交予mDrawerToggle处理，处理不了就交予系统处理
		return mDrawerToggle.onOptionsItemSelected(item)
				| super.onOptionsItemSelected(item);
	}

	private void openSearch() {

	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	private boolean isExit = false;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (!isExit) {
				isExit = true;// 准备退出的标志

				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

				/*
				 * 设置会自动执行的TimerTask， 如果在2秒钟之内不再一次按下返回键，即将isExit设为false，即取消掉准备退出
				 * 如果在2秒钟之内再一次按下返回键，此时isExit仍为true，此时执行else中的方法
				 */
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// 取消准备退出的标志
						isExit = false;
					}

				}, 2000);

			} else {
				BaseActivity.killAll();
			}
		}
		return true;
	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// }

}
