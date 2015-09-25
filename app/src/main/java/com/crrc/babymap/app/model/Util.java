package com.crrc.babymap.app.model;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Carlos on 22/09/2015.
 */
public class Util {

	public static void setScreenSizes(Activity pActivity){

		DisplayMetrics displaymetrics = new DisplayMetrics();
		pActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		UserProfile.getMainUserProfile().setScreenHeight(displaymetrics.heightPixels);
		UserProfile.getMainUserProfile().setScreenWidth(displaymetrics.widthPixels);
	}
/*
	Qualifier   DPI   Scaling factor(px/dp)
		mdpi 	    160 	      1.0
		hdpi 	    240 	      1.5
		xhdpi 	  320 	      2.0
		xxhdpi 	  480 	      3.0
*/
	public static int dpToPx(Context pContext, int dp) {
		DisplayMetrics displayMetrics = pContext.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_MEDIUM));
		return px;
	}
	public static int pxToDp(Context pContext, int px) {
		DisplayMetrics displayMetrics = pContext.getResources().getDisplayMetrics();
		int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_MEDIUM));
		return dp;
	}
}
