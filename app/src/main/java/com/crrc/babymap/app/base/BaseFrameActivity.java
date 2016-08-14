package com.crrc.babymap.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.crrc.babymap.R;


/**
 * Created by carlos on 19/07/2016.
 */
public abstract class BaseFrameActivity<FirstFragment extends Fragment> extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        if (savedInstanceState != null) {
            return;
        }
        loadFirstFragment();
    }

    protected int getLayout() {
        return R.layout.frame;}

    protected void loadFirstFragment() {
        FirstFragment firstFragment = getFirstFragment();
        if (firstFragment == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction().add(getFrameId(), firstFragment).commit();
    }

    private int getFrameId() {
        return R.id.frame;}

    @Override
    public void onBackPressed() {
        if (performFragmentBackButton()) {
            return;
        }
        super.onBackPressed();
    }

    private boolean performFragmentBackButton() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(getFrameId());
        if (fragment instanceof BackButtonOverride) {
            if (((BackButtonOverride) fragment).onBackPressed()) {
                return true;
            }
        }
        return false;
    }

    protected abstract FirstFragment getFirstFragment();
}

