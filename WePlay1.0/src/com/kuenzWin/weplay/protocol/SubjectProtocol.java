package com.kuenzWin.weplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kuenzWin.weplay.base.BaseProtocol;
import com.kuenzWin.weplay.domain.SubjectInfo;

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {

	@Override
	protected List<SubjectInfo> paserJson(String json) {

		List<SubjectInfo> infos = null;
		try {
			JSONArray array = new JSONArray(json);
			infos = new ArrayList<SubjectInfo>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				String des = object.getString("des");
				String url = object.getString("url");
				SubjectInfo info = new SubjectInfo(des, url);
				infos.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}

	@Override
	protected String getKey() {
		return "subject";
	}

}
