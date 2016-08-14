package com.crrc.babymap.app.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.crrc.babymap.app.util.DialogUtil;
import com.crrc.babymap.app.util.Util;
import com.trello.rxlifecycle.components.support.RxDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by carlos on 19/07/2016.
 */
public class BaseDialogFragment  extends RxDialogFragment {

    private ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Util.freeMemory();
    }

    public void showProgressDialog(String message) {
        dismissProgressDialog();
        progressDialog = ProgressDialog.show(getActivity(), message, "please wait");
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.show();
    }

    public void showToast(String message) {
        DialogUtil.showToast(getContext(), message);
    }

    public void showErrorToast(Throwable e) {
        DialogUtil.showErrorToast(getContext(), e);
    }

    protected void setToolbar(String objectName) {
        ((BaseToolbarActivity) getActivity()).setupToolbar(objectName, null);
    }

    public void launchBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
