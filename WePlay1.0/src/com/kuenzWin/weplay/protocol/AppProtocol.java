package com.kuenzWin.weplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kuenzWin.weplay.base.BaseProtocol;
import com.kuenzWin.weplay.domain.AppInfo;

public class AppProtocol extends BaseProtocol<List<AppInfo>> {

	@Override
	protected List<AppInfo> paserJson(String json) {
		
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				long id = object.getLong("id");
				String name = object.getString("name");
				String packageName = object.getString("packageName");
				String iconUrl = object.getString("iconUrl");
				float stars = Float.parseFloat(object.getString("stars"));
				long size = object.getLong("size");
				String downloadUrl = object.getString("downloadUrl");
				String des = object.getString("des");
				AppInfo info = new AppInfo(id, name, packageName, iconUrl,
						stars, size, downloadUrl, des);
				appInfos.add(info);
			}
			return appInfos;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getKey() {
		return "app";
	}

}
