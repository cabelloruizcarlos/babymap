package com.crrc.babymap.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.crrc.babymap.R;
import com.crrc.babymap.app.interfaces.ISplashView;
import com.crrc.babymap.app.model.Constant;
import com.crrc.babymap.app.model.UserProfile;
import com.crrc.babymap.app.model.Util;
import com.crrc.babymap.app.presenters.SplashPresenter;

public class SplashScreenActivity extends Activity implements ISplashView{

    SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mSplashPresenter = new SplashPresenter(this);
        mSplashPresenter.initApp(getPreferences(MODE_PRIVATE));
    }

    public void navigateToMapsActivity() {

        final Intent intent = new Intent(this, MapsActivity.class);
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_left);
                        finish();
                    }
                }, Constant.SPLASH_DELAY);

    }

    public Activity getActivity(){
        return this;
    }
}
