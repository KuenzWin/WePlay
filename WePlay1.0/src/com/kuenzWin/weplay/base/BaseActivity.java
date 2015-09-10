package com.kuenzWin.weplay.base;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

	/**
	 * 用来记录打开的Activity，在程序退出时进行逐一销毁
	 */
	private static final List<BaseActivity> mActivitys = new LinkedList<BaseActivity>();
	public static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		// 由于在测试中有可能因当前打开的Activity还未添加到集合中就被删除会
		// 出现线程安全问题，所以加上同步代码块(monkey测试可能会出现此问题)
		synchronized (mActivitys) {
			mActivitys.add(this);
		}

		init();

		initView();

		initActionBar();

	}

	protected abstract void init();

	protected abstract void initView();

	protected void initActionBar() {
		ActionBar mActionBar = this.getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivitys) {
			mActivitys.remove(this);
		}
	}

	/**
	 * 在程序结束时调用，可以销毁所有的Activity，回收内存
	 */
	public static void killAll() {
		List<BaseActivity> copy = null;
		synchronized (mActivitys) {
			copy = new LinkedList<BaseActivity>(mActivitys);
		}
		for (Activity activity : copy) {
			activity.finish();
		}
		// 杀死当前进程，结束程序运行
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
