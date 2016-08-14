package com.crrc.babymap.app.base;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.MenuItem;

import com.crrc.babymap.app.util.DialogUtil;
import com.crrc.babymap.app.util.Util;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Base activity for all other fragments in the app to extend from - put shared methods in here
 */
public abstract class BaseActivity  extends RxAppCompatActivity {
    private Unbinder unbinder;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        Util.freeMemory();
    }

    public void showToast(String message) {
        DialogUtil.showToast(this, message);
    }

    public void showErrorToast(Throwable e) {
        DialogUtil.showErrorToast(this, e);
    }

    public void showAlert(String title, String message) {
        DialogUtil.showAlert(this, title, message);
    }

    public void launchBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }


}
