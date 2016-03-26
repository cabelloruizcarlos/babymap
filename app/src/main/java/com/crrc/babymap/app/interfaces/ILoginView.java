package com.crrc.babymap.app.interfaces;

import android.content.Context;

/**
 * Created by Carlos2 on 26/03/2016.
 */
public interface ILoginView {

    void loadUser();
    void navigateToMapsActivity();

    Context getApplicationContext();
}
