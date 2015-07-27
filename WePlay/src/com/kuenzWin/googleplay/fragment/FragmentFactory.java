package com.kuenzWin.googleplay.fragment;

import java.util.HashMap;

import com.kuenzWin.googleplay.base.BaseFragment;
import com.kuenzWin.googleplay.utils.LogUtils;

/**
 * Fragment工厂
 * 
 * @author 温坤哲
 * @date 2015-6-10
 */
public class FragmentFactory {

	private static final int Title_HOME = 0;
	private static final int Title_APP = 1;
	private static final int Title_GAME = 2;
	private static final int Title_SUBJECT = 3;
	private static final int Title_RECOMMENT = 4;
	private static final int Title_CATEGORY = 5;
	private static final int Title_HOT = 6;

	private static HashMap<Integer, BaseFragment> mHashMap = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position) {

		BaseFragment fragment = mHashMap.get(position);

		if (fragment == null) {
			switch (position) {
			case Title_HOME:
				LogUtils.d("home");
				fragment = new HomeFramgent();
				break;
			case Title_APP:
				fragment = new AppFramgent();
				break;
			case Title_GAME:
				fragment = new GameFramgent();
				break;
			case Title_SUBJECT:
				fragment = new SubjectFramgent();
				break;
			case Title_RECOMMENT:
				fragment = new RecommentFramgent();
				break;
			case Title_CATEGORY:
				fragment = new CategoryFramgent();
				break;
			case Title_HOT:
				fragment = new HotFragment();
				break;
			}
			mHashMap.put(position, fragment);
		}
		return fragment;
	}

}
