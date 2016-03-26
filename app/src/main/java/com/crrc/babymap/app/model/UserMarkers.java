package com.crrc.babymap.app.model;

import com.crrc.babymap.app.retrofit.IMarker;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 09/04/2015.
 */
public class UserMarkers {

    private int mUser_id;
    private List<Marker> mMarkersList = new ArrayList<Marker>();
    private IMarker listener;

    public UserMarkers() {
    }

    public UserMarkers(List<Marker> pMarkersList, int pUser_id) {

        this.mMarkersList = pMarkersList;
        this.mUser_id = pUser_id;
    }

    public Marker getMarker(int pos) {

        if ((pos >= 0) && (pos < this.mMarkersList.size()))
            return this.mMarkersList.get(pos);
        return null;
    }

    public int getPositionMarker(MarkerOptions pMarker) {

        if ((pMarker != null) && (this.mMarkersList.size() > 0)) {
            for (int i = 0; i < this.mMarkersList.size(); i++) {
                // TODO: I am comparing a Marker.getPosition() and MarkerOptions.getPosition() without checking it before
                if (this.mMarkersList.get(i).getPosition().equals(pMarker.getPosition()))
                    return i;
            }
        }
        return -1;
    }

    public void addMarker(MarkerOptions pMarker) {

        int pos = getPositionMarker(pMarker);
        if (pos == -1) {

            Marker newMarker = this.listener.addNewMarker(pMarker);
            this.mMarkersList.add(pos, newMarker);
        }
    }

    public void removeMarker(MarkerOptions pMarker) {

        int pos = getPositionMarker(pMarker);
        if (pos >= 0) {

            this.mMarkersList.remove(pos);
            this.listener.removeOldMarker(pMarker);
        }
    }

    public List<Marker> getAllMarkers() {
        return this.mMarkersList;
    }

    public void setAllMarkers(List<Marker> markers) {
        this.mMarkersList = markers;
    }

    /*
 * creating random position around a location for testing purpose only
 */
    private double[] createRandLocation(double latitude, double longitude) {

        return new double[]{latitude + ((Math.random() - 0.5) / 500),
                longitude + ((Math.random() - 0.5) / 500),
                150 + ((Math.random() - 0.5) * 10)};
    }

    public int size() {
        return this.mMarkersList.size();
    }
}
