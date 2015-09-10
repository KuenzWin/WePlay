package com.kuenzWin.weplay.volley;

import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.kuenzWin.weplay.base.BaseApplication;

public class VolleyRequest {

	private static StringRequest stringRequest;

	/**
	 * get请求
	 * 
	 * @param mContext
	 *            上下文对象
	 * @param url
	 *            请求的url
	 * @param tag
	 *            request的tag标签
	 * @param vif
	 *            集成Response.Listener接口和Response.ErrorListener接口的新抽象类
	 */
	public static void get(String url, String tag, VolleyInterface vif) {
		cancelRequest(tag);
		getStringRequest(url, vif);
		request2(tag);
	}

	/**
	 * 不带参数的request
	 * 
	 * @param url
	 *            request的url
	 * @param vif
	 *            集成Response.Listener接口和Response.ErrorListener接口的新抽象类
	 */
	private static void getStringRequest(String url, VolleyInterface vif) {
		stringRequest = new StringRequest(Method.GET, url, vif.getListener(),
				vif.getErrorListener());
	}

	/**
	 * post请求
	 * 
	 * @param mContext
	 *            上下文对象
	 * @param url
	 *            request的url
	 * @param tag
	 *            request的标签
	 * @param map
	 *            request的参数，以Map形式传递
	 * @param vif
	 *            集成Response.Listener接口和Response.ErrorListener接口的新抽象类
	 */
	public static void post(Context mContext, String url, String tag,
			final Map<String, String> map, VolleyInterface vif) {
		cancelRequest(tag);
		getStringRequest(url, map, vif);
		request2(tag);
	}

	/**
	 * 取消tag标签的Request
	 * 
	 * @param tag
	 */
	private static void cancelRequest(String tag) {
		BaseApplication.getRequestQueue().cancelAll(tag);
	}

	/**
	 * 将request加入到request队列中并执行
	 * 
	 * @param tag
	 */
	private static void request2(String tag) {
		stringRequest.setTag(tag);
		BaseApplication.getRequestQueue().add(stringRequest);
		BaseApplication.getRequestQueue().start();
	}

	/**
	 * 获取带参数map的StringRequest
	 * 
	 * @param url
	 *            request的url
	 * @param map
	 *            请求参数map
	 * @param vif
	 *            集成Response.Listener接口和Response.ErrorListener接口的新抽象类
	 */
	private static void getStringRequest(String url,
			final Map<String, String> map, VolleyInterface vif) {
		stringRequest = new StringRequest(Method.GET, url, vif.getListener(),
				vif.getErrorListener()) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}
		};
	}
}
