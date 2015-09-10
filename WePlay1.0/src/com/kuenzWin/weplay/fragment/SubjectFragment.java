package com.kuenzWin.weplay.fragment;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.adapter.CommonAdapter;
import com.kuenzWin.weplay.base.BaseFragment;
import com.kuenzWin.weplay.base.BaseListViewHolder;
import com.kuenzWin.weplay.domain.SubjectInfo;
import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.protocol.SubjectProtocol;
import com.kuenzWin.weplay.utils.UIUtils;
import com.kuenzWin.weplay.view.BaseListView;
import com.kuenzWin.weplay.view.LoadingPage.LoadResult;

public class SubjectFragment extends BaseFragment {

	private List<SubjectInfo> datas;
	private SubjectAdapter adapter;

	@Override
	protected LoadResult onLoad() {
		SubjectProtocol sp = new SubjectProtocol();
		datas = sp.load(0);
		return checkData(sp.load(0));
	}

	@Override
	protected View createSuccessView() {
		BaseListView lv = new BaseListView(UIUtils.getContext());
		adapter = new SubjectAdapter(UIUtils.getContext(), datas,
				R.layout.item_subject);
		lv.setAdapter(adapter);
		return lv;
	}

	private class SubjectAdapter extends CommonAdapter<SubjectInfo> {

		public SubjectAdapter(Context context, List<SubjectInfo> datas,
				int layoutId) {
			super(context, datas, layoutId);
		}

		@Override
		public void setData(BaseListViewHolder holder, SubjectInfo info) {
			ImageView item_icon = (ImageView) holder.getView(R.id.item_icon);
			mBitmapUtils.display(item_icon, HttpHelper.URL + "image?name="
					+ info.getUrl());
			((TextView) holder.getView(R.id.item_txt)).setText(info.getDes());
		}

		@Override
		protected List<SubjectInfo> onLoadMore() {

			SubjectProtocol sp = new SubjectProtocol();
			return sp.load(datas.size());
		}

	}

}
