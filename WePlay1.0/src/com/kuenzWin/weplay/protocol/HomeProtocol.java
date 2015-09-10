package com.kuenzWin.weplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kuenzWin.weplay.base.BaseProtocol;
import com.kuenzWin.weplay.domain.AppInfo;

public class HomeProtocol extends BaseProtocol<List<AppInfo>> {

	String json = null;

	protected String getKey() {
		return "home";
	}

	private List<String> pictures;

	public List<String> getPictures() {
		return pictures;
	}

	/**
	 * json解析
	 * 
	 * @param json2
	 */
	protected List<AppInfo> paserJson(String json) {
		if (json == null)
			return null;
		try {
			JSONObject object = new JSONObject(json);

			JSONArray array = object.getJSONArray("picture");
			pictures = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				String picture = array.getString(i);
				pictures.add(picture);
			}

			array = object.getJSONArray("list");
			List<AppInfo> infos = new ArrayList<AppInfo>();
			for (int i = 0; i < array.length(); i++) {

				JSONObject jsonObj = array.getJSONObject(i);
				long id = jsonObj.getLong("id");
				String name = jsonObj.getString("name");
				String packageName = jsonObj.getString("packageName");
				String iconUrl = jsonObj.getString("iconUrl");
				float stars = Float.parseFloat(jsonObj.getString("stars"));
				long size = jsonObj.getLong("size");
				String downloadUrl = jsonObj.getString("downloadUrl");
				String des = jsonObj.getString("des");
				AppInfo info = new AppInfo(id, name, packageName, iconUrl,
						stars, size, downloadUrl, des);
				infos.add(info);
			}
			return infos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
