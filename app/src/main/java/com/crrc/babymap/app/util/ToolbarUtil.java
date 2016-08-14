package com.crrc.babymap.app.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by carlos on 25/07/2016.
 */
public class ToolbarUtil {

  public static void setupToolbarView(AppCompatActivity activity, Toolbar toolbar) {
    activity.setSupportActionBar(toolbar);
    ActionBar actionBar = activity.getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
  }

  public static void setupToolbarViewNoBackBtn(AppCompatActivity activity, Toolbar toolbar) {
    activity.setSupportActionBar(toolbar);
    ActionBar actionBar = activity.getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
    actionBar.setDisplayShowTitleEnabled(false);
  }
}
