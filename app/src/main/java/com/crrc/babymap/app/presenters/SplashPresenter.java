package com.crrc.babymap.app.presenters;

import android.content.SharedPreferences;

import com.crrc.babymap.app.interfaces.ISplashPresenter;
import com.crrc.babymap.app.interfaces.ISplashView;
import com.crrc.babymap.app.model.UserProfile;
import com.crrc.babymap.app.util.Util;

/**
 * Created by Carlos on 03/03/2016.
 */
public class SplashPresenter implements ISplashPresenter {

    ISplashView mView;

    public SplashPresenter(ISplashView view) {
        this.mView = view;
    }

    public void initApp(SharedPreferences pPreferences) {
        UserProfile.initMainUserProfile(pPreferences);

        //TODO: While we didnt decided how to set the security of the app. The user_id is going to be always 0
        UserProfile.getMainUserProfile().setUser_id(0);
        Util.setScreenSizes(mView.getActivity());

        mView.navigateToMapsActivity();
    }


}
