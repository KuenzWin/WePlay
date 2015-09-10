package com.kuenzWin.weplay.factory;

import android.util.SparseArray;

import com.kuenzWin.weplay.base.BaseFragment;
import com.kuenzWin.weplay.fragment.AppFragment;
import com.kuenzWin.weplay.fragment.CategoryFragment;
import com.kuenzWin.weplay.fragment.GameFragment;
import com.kuenzWin.weplay.fragment.HomeFragment;
import com.kuenzWin.weplay.fragment.SubjectFragment;
import com.kuenzWin.weplay.fragment.TopFragment;

public class FragmentFactoty {

	/**
	 * fragment缓存SparseArray,SparseArray内部嵌入了一个Integer的键值，等同于优化过的Map
	 */
	private static SparseArray<BaseFragment> mFragments = new SparseArray<BaseFragment>();;

	public static BaseFragment createFragment(int position) {

		BaseFragment fragment = mFragments.get(position);

		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new CategoryFragment();
				break;
			case 5:
				fragment = new TopFragment();
			}
			mFragments.put(position, fragment);
		}
		return fragment;
	}

}
