package com.kuenzWin.weplay.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import android.os.SystemClock;

import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.http.HttpHelper.HttpResult;
import com.kuenzWin.weplay.utils.FileUtils;
import com.lidroid.xutils.util.IOUtils;

public abstract class BaseProtocol<T> {

	public T load(int index) {
		SystemClock.setCurrentTimeMillis(2 * 1000L);
		String json = loadLocal(index);
		if (json == null) {
			json = loadServer(index);
			saveLocal(json, index);
		}
		return paserJson(json);
	}

	/**
	 * 将json保存到本地中，作为缓存
	 * 
	 * @param json
	 * @param index
	 */
	private void saveLocal(String json, int index) {
		if (json == null)
			return;
		BufferedWriter bw = null;
		try {
			File dir = FileUtils.getCacheDir();
			// 在第一行写一个过期时间
			File file = new File(dir, getKey() + "_" + index + this.getParams()); // /mnt/sdcard/googlePlay/cache/home_0
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			// 将过期时间保存至文件中，即获取的数据只保存100秒
			bw.write(System.currentTimeMillis() + 1000 * 100 + "");
			bw.newLine();// 换行
			bw.write(json);// 把整个json文件保存起来
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}

	/**
	 * 从服务器加载json数据
	 */
	private String loadServer(final int index) {
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey()
				+ "?index=" + index + getParams());
		if (httpResult != null) {
			String json = httpResult.getString();
			return json;
		} else {
			return null;
		}
	}

	protected String getParams() {
		return "";
	}

	/**
	 * 读取本地缓存数据
	 * 
	 * @return
	 */
	private String loadLocal(int index) {
		// 如果发现文件已经过期了 就不要再去复用缓存了
		File dir = FileUtils.getCacheDir();// 获取缓存所在的文件夹
		File file = new File(dir, getKey() + "_" + index + this.getParams());
		BufferedReader br = null;
		String result = null;
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			long outOfDate = Long.parseLong(br.readLine());
			if (System.currentTimeMillis() > outOfDate) {
				return null;
			} else {
				String str = null;
				StringWriter sw = new StringWriter();
				while ((str = br.readLine()) != null) {
					sw.write(str);
				}
				result = sw.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return result;

	}

	/**
	 * 解析json文件，获取展示在界面上的数据
	 * 
	 * @param json
	 * @return
	 */
	protected abstract T paserJson(String json);

	/**
	 * 获取标志位：homeProtocol为home等
	 * 
	 * @return
	 */
	protected abstract String getKey();
}
