package com.kuenzWin.googleplay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import com.kuenzWin.googleplay.base.BaseActivity;
import com.kuenzWin.googleplay.base.BaseFragment;
import com.kuenzWin.googleplay.fragment.FragmentFactory;
import com.kuenzWin.googleplay.utils.UIUtils;
import com.kuenzWin.googleplay.view.PagerTab;

/**
 * @author 温坤哲
 * @date 2015-6-3
 */
public class MainActivity extends BaseActivity implements OnPageChangeListener {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void initActionBar() {
		ActionBar ab = this.getSupportActionBar();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);

		PagerTab pt_main = (PagerTab) this.findViewById(R.id.pt_main);
		ViewPager pager_main = (ViewPager) this.findViewById(R.id.pager_main);
		MyPagerAdapter adapter = new MyPagerAdapter(
				this.getSupportFragmentManager());
		pager_main.setAdapter(adapter);
		pt_main.setViewPager(pager_main);
		pt_main.setOnPageChangeListener(this);
	}

	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		private String[] tab_names;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			tab_names = UIUtils.getStringArray(R.array.tab_names);
		}

		@Override
		public int getCount() {
			return tab_names.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		BaseFragment fragment = FragmentFactory.createFragment(position);
		fragment.show();
	}

}
