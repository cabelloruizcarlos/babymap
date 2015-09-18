package com.crrc.babymap.interfaces;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Carlos on 09/04/2015.
 */
public interface IMarker {

    Marker addNewMarker(MarkerOptions pMarker);
    void removeOldMarker(MarkerOptions pMarker);
}
