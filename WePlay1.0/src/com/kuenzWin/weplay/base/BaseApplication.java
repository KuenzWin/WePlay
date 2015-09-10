package com.kuenzWin.weplay.base;

import android.app.Application;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class BaseApplication extends Application {

	private static Application application;
	private static int mainTid;
	private static Handler handler;
	private static RequestQueue requestQueue;

	public static Application getApplication() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		mainTid = android.os.Process.myPid();
		handler = new Handler();
		requestQueue = Volley.newRequestQueue(this);
	}

	public static int getMainTid() {
		return mainTid;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static RequestQueue getRequestQueue() {
		return requestQueue;
	}

}
