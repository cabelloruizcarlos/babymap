package com.crrc.babymap.app.interfaces;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Carlos2 on 26/03/2016.
 */
public interface IMapsView {

    void loadMenu();
    void setUpMapIfNeeded();
    void showMapsActualLocation();

    GoogleMap getMapView();
    Activity getMapsActivity();
}
