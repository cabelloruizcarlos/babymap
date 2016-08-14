package com.crrc.babymap.app.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.crrc.babymap.app.util.DialogUtil;
import com.crrc.babymap.app.util.Util;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Base fragment for all other fragments in the app to extend from - put shared methods in here
 */
public class BaseFragment extends RxFragment {
    private Unbinder unbinder;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Util.freeMemory();
        Log.d("GC call" , "BaseFragment");
    }

    public void showAlert(String title, String message) {
        DialogUtil.showAlert(mContext, title, message);
    }

    public void showToast(String message) {
        DialogUtil.showToast(mContext, message);
    }

    public void showErrorToast(Throwable e) {
        DialogUtil.showErrorToast(mContext, e);
    }


    public void launchBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
