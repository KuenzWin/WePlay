package com.kuenzWin.weplay.fragment;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuenzWin.weplay.R;
import com.kuenzWin.weplay.adapter.CommonAdapter;
import com.kuenzWin.weplay.base.BaseFragment;
import com.kuenzWin.weplay.base.BaseListViewHolder;
import com.kuenzWin.weplay.domain.CategoryInfo;
import com.kuenzWin.weplay.holder.TitleHolder;
import com.kuenzWin.weplay.http.HttpHelper;
import com.kuenzWin.weplay.protocol.CategoryProtocol;
import com.kuenzWin.weplay.view.BaseListView;
import com.kuenzWin.weplay.view.LoadingPage.LoadResult;

public class CategoryFragment extends BaseFragment {

	private List<CategoryInfo> mData;

	protected LoadResult onLoad() {
		mData = new CategoryProtocol().load(0);
		return checkData(mData);
	}

	protected View createSuccessView() {
		BaseListView mListView = new BaseListView(mContext);
		mListView.setAdapter(new CategoryAdapter(mContext, mData,
				R.layout.item_category_content));
		return mListView;
	}

	private class CategoryAdapter extends CommonAdapter<CategoryInfo> {

		// 这个值不能随意写，按照顺序0.1.2,所以应写成getViewTypeCount()返回值为3，否则爆出数组越界异常
		private static final int TYPE_TITLE = 2;

		public CategoryAdapter(Context context, List<CategoryInfo> mDatas,
				int layoutId) {
			super(context, mDatas, layoutId, false);
		}

		@Override
		public int getViewTypeCount() {
			// 0、1、2
			return 3;
		}

		@Override
		public int getItemViewType(int position) {
			if (mDatas.get(position).isTitle()) {
				return TYPE_TITLE;
			} else {
				return super.getItemViewType(position);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (getItemViewType(position) == TYPE_TITLE) {
				TitleHolder titleHolder = TitleHolder.get(mContext,
						convertView, parent, position);
				titleHolder.setText(R.id.tv_category_title, mDatas
						.get(position).getTitle());
				return titleHolder.getConvertView();
			}
			return super.getView(position, convertView, parent);
		}

		@Override
		public void setData(BaseListViewHolder holder, CategoryInfo data) {

			ImageView iv1 = holder.getView(R.id.iv_1);
			ImageView iv2 = holder.getView(R.id.iv_2);
			ImageView iv3 = holder.getView(R.id.iv_3);

			TextView tv1 = holder.getView(R.id.tv_1);
			TextView tv2 = holder.getView(R.id.tv_2);
			TextView tv3 = holder.getView(R.id.tv_3);

			// 第一块
			if (!TextUtils.isEmpty(data.getName1())
					&& !TextUtils.isEmpty(data.getUrl1())) {
				tv1.setText(data.getName1());
				mBitmapUtils.display(iv1,
						HttpHelper.URL + "image?name=" + data.getUrl1());
				tv1.setVisibility(View.VISIBLE);
				iv1.setVisibility(View.VISIBLE);
			} else {
				tv1.setVisibility(View.INVISIBLE);
				iv1.setVisibility(View.INVISIBLE);
			}
			// 第二块
			if (!TextUtils.isEmpty(data.getName2())
					&& !TextUtils.isEmpty(data.getUrl2())) {
				tv2.setText(data.getName2());
				mBitmapUtils.display(iv2,
						HttpHelper.URL + "image?name=" + data.getUrl2());
				tv2.setVisibility(View.VISIBLE);
				iv2.setVisibility(View.VISIBLE);
			} else {
				tv2.setVisibility(View.INVISIBLE);
				iv2.setVisibility(View.INVISIBLE);
			}
			// 第三块
			if (!TextUtils.isEmpty(data.getName3())
					&& !TextUtils.isEmpty(data.getUrl3())) {
				tv3.setText(data.getName3());
				mBitmapUtils.display(iv3,
						HttpHelper.URL + "image?name=" + data.getUrl3());
				tv3.setVisibility(View.VISIBLE);
				iv3.setVisibility(View.VISIBLE);
			} else {
				tv3.setVisibility(View.INVISIBLE);
				iv3.setVisibility(View.INVISIBLE);
			}
			setOnClickListener(holder, mDatas.indexOf(data));
		}

		private void setOnClickListener(BaseListViewHolder holder,
				final int position) {

			RelativeLayout rl1 = holder.getView(R.id.rl_1);
			RelativeLayout rl2 = holder.getView(R.id.rl_2);
			RelativeLayout rl3 = holder.getView(R.id.rl_3);

			rl1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, mDatas.get(position).getName1(),
							Toast.LENGTH_SHORT).show();
				}
			});

			rl2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, mDatas.get(position).getName2(),
							Toast.LENGTH_SHORT).show();
				}
			});

			rl3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, mDatas.get(position).getName3(),
							Toast.LENGTH_SHORT).show();
				}
			});

		}

	}

}
