package com.crrc.babymap.app.views;

/**
 * Created by Carlos on 23/09/2015.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crrc.babymap.R;

public class HScrollItemView extends RelativeLayout{
	private final static String TAG = "HScrollItemView";
	private int mWidth;

	public HScrollItemView(Context context) {
		super(context);
		this.init(context, null, 0);
	}

	public HScrollItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context, attrs, 0);
	}

	public HScrollItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init(context, attrs, defStyle);
	}

	/*@Override
	protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
		super.onLayout(b, i, i2, i3, i4);
		mWidth = i3 - i;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec,
	                         int heightMeasureSpec) {
		int width = mWidth; // MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
*/
	private void init(Context context, AttributeSet attrs, int defStyle) {
		View.inflate(context, R.layout.hscrollview_item, this);
	}
/*
	public String getText() {
		TextView captionView = (TextView) findViewById(R.id.hscrollview_caption);
		return captionView.getText().toString();
	}

	public void setTab(Object tab, int position) {
		TextView textView = (TextView) findViewById(R.id.hscrollview_caption);
		textView.setText(tab.toString());

		ImageButton button = (ImageButton) findViewById(R.id.hscrollview_button);
		if ((position%2)==0)
			button.setBackgroundColor(getResources().getColor(R.color.primaryColor));
		else
			button.setBackgroundColor(getResources().getColor(R.color.colorAccent));

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "You cliked the item in the list", Toast.LENGTH_SHORT).show();
			}
		});
	}*/
}

