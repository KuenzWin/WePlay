package com.kuenzWin.googleplay.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * @author 温坤哲
 * @date 2015-6-3
 */
public abstract class BaseActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		initView();
	}

	protected abstract void initActionBar();

	protected abstract void initView();

}
