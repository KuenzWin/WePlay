package com.kuenzWin.weplay.volley;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * 集成Response.Listener接口和Response.ErrorListener接口的新抽象类
 * 
 * @author KuenzWin
 * @date 2015-8-13
 */
public abstract class VolleyInterface {

	private Listener<String> mListener;
	private ErrorListener mErrorListener;

	public Listener<String> getListener() {
		return mListener;
	}

	public ErrorListener getErrorListener() {
		return mErrorListener;
	}

	public VolleyInterface() {
		initListener();
		initErrorListener();
	}

	/**
	 * 实例化request请求成功的回调接口
	 * 
	 * @return request请求成功的回调接口
	 */
	private Listener<String> initListener() {
		mListener = new Listener<String>() {

			@Override
			public void onResponse(String result) {
				onSuccess(result);
			}
		};
		return mListener;
	}

	/**
	 * 实例化request请求失败的回调接口
	 * 
	 * @return request请求失败的回调接口
	 */
	private ErrorListener initErrorListener() {
		mErrorListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				onError(error);
			}
		};
		return mErrorListener;
	}

	/**
	 * request请求成功的回调方法
	 * 
	 * @param result
	 */
	public abstract void onSuccess(String result);

	/**
	 * request请求失败的回调方法
	 * 
	 * @param error
	 */
	public abstract void onError(VolleyError error);
}
