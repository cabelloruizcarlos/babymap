package com.crrc.babymap.app.adapters;

/**
 * Created by Carlos on 23/09/2015.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.crrc.babymap.app.views.HScrollItemView;

import java.util.ArrayList;
import java.util.List;

public class HScrollViewAdapter extends BaseAdapter {
	private final static String TAG = "HScrollViewAdapter";

	private final ArrayList<Object> mTabs = new ArrayList<>();
	private float mWidth;
	private Context mContext;

	public HScrollViewAdapter(Context pContext, List<?> pTabs, float pWidth) {
		this.mContext = pContext;
		this.mTabs.addAll(pTabs);
		this.mWidth = pWidth;
	}

	public float getItemWidth() {
		return this.mWidth;
	}

	public void setItemWidth(int pWidth) {
		this.mWidth = pWidth;
		this.notifyDataSetInvalidated();
	}

	@Override
	public int getCount() {
		return this.mTabs.size();
	}

	@Override
	public Object getItem(int i) {
		return this.mTabs.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		HScrollItemView vi;

		if (view == null) {
			vi = new HScrollItemView(this.mContext);
		}
		else { // Recycling view.
			vi = (HScrollItemView)view;
		}
/*		vi.setTab(mTabs.get(position), position);
		vi.layout(0, 0, (int) this.mWidth, viewGroup.getHeight());*/

		return vi;
	}
}

