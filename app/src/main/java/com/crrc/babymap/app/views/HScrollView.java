package com.crrc.babymap.app.views;

/**
 * Created by Carlos on 23/09/2015.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crrc.babymap.R;
import com.crrc.babymap.app.model.UserProfile;

import java.util.ArrayList;

public class HScrollView extends RelativeLayout {

	private RelativeLayout mParentLayout;

	private int mItemWidth;
	private int mNumItems;
	private ArrayList<String> mTabs;

	public HScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private synchronized void initView() {
		View.inflate(getContext(), R.layout.hscrollview_layout, this);
		this.mParentLayout = (RelativeLayout) findViewById(R.id.hscrollview_layout);
	}

	public void setTabs(ArrayList<String> pTabs) {

		mNumItems = pTabs.size();
		mTabs = new ArrayList<String>(mNumItems);
		mTabs = pTabs;
		setItemWidth();
	}

	public int getItemWidth() {
		return (int) mItemWidth;
	}

	public void setItemWidth() {
		mItemWidth = (int) (UserProfile.getMainUserProfile().getScreenWidth() / mNumItems);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(this.mItemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
		int id=0;
	}
}