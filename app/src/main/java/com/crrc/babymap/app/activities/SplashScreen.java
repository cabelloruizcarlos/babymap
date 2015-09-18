package com.crrc.babymap.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.crrc.babymap.R;
import com.crrc.babymap.model.Constant;
import com.crrc.babymap.model.UserProfile;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        initApp(getPreferences(MODE_PRIVATE));

        // If the user is already logged we move forward to the main activity
        Character nextActivity = 'l';
        if (UserProfile.isLoggedOut()) {
            nextActivity = 'm';
        }

        final Intent intent;
        switch (nextActivity) {
            case 'm': {
                intent = new Intent(this, MapsActivity.class);
                break;
            }
            default: {
                intent = new Intent(this, LoginActivity.class);
                break;
            }
        }

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_left);
                        SplashScreen.this.finish();
                    }
                }, Constant.SPLASH_DELAY);
    }

    private void initApp(SharedPreferences pPreferences) {
        UserProfile.initMainUserProfile(pPreferences);

//      TODO: While we didnt decided how to set the security of the app. The user_id is going to be always 0
        UserProfile.getMainUserProfile().setUser_id(0);
    }
}
