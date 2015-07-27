package com.kuenzWin.googleplay.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * @author 温坤哲
 * @date 2015-6-3
 */
public class BaseApplication extends Application {
	/**
	 * 主线程的上下文
	 */
	private static BaseApplication mContext = null;
	/**
	 * 主线程的handler
	 */
	private static Handler mMainThreadHandler = null;
	/**
	 * 主线程的looper
	 */
	private static Looper mMainThreadLooper = null;
	/**
	 * 主线程本身
	 */
	private static Thread mMainThead = null;
	/**
	 * 主线程的id
	 */
	private static int mMainTheadId;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this;
		mMainThreadHandler = new Handler();
		mMainThreadLooper = getMainLooper();
		mMainThead = Thread.currentThread();
		// android.os.Process.myUid() 获取到用户id
		// android.os.Process.myPid()获取到进程id
		// android.os.Process.myTid()获取到调用线程的id
		mMainTheadId = android.os.Process.myTid();
	}

	public static BaseApplication getApplication() {
		return mContext;
	}

	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	public static Looper getMainThreadLooper() {
		return mMainThreadLooper;
	}

	public static Thread getMainThread() {
		return mMainThead;
	}

	public static int getMainThreadId() {
		return mMainTheadId;
	}

}
