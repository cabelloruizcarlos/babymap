package com.crrc.babymap.app.interfaces;

import android.content.Context;

import com.crrc.babymap.app.mvp.BaseView;

/**
 * Created by Carlos2 on 26/03/2016.
 */
public interface LoginView extends BaseView{

    void loadUser();
    void navigateToMapsActivity();

    Context getApplicationContext();
}
