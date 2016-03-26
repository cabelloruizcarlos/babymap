package com.crrc.babymap.app.presenters;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.crrc.babymap.R;
import com.crrc.babymap.app.interfaces.IMapsView;
import com.crrc.babymap.app.model.UserMarkers;
import com.crrc.babymap.app.model.UserProfile;
import com.crrc.babymap.app.retrofit.IMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos2 on 26/03/2016.
 */
public class MapsPresenter implements IMarker {
    private String TAG = MapsPresenter.class.getSimpleName();

    private IMapsView mView;
    private Activity mViewActivity;
    private GoogleMap mMapView;
    private List<MarkerOptions> mMarkerOptionsList;

    public MapsPresenter(IMapsView view) {
        this.mView = view;
        this.mViewActivity = this.mView.getMapsActivity();
    }

    public void initView(){

        /*Set up the menu UI*/
        this.mView.loadMenu();

        /*Create and set up the map*/
        this.mView.setUpMapIfNeeded();

        /*Move to the current location*/
        this.mView.showMapsActualLocation();

        this.mMapView = this.mView.getMapView();
        this.mMapView.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "You click on " + latLng.latitude + "lat, " + latLng.longitude + "long");
            }
        });

        /*Customization of the marker`s dialog*/
        this.mMapView.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Getting view from the layout file info_window_layout
                View v = mViewActivity.getLayoutInflater().inflate(R.layout.activity_custom_info_window, null);

                // Returning the view containing InfoWindow contents
                return v;
            }
        });

        /*Retrieve the data from the server, save it in the this.mMarkerOptionsList array and adding it into the maps*/
        retrieveFromTheServer();
    }

    private void retrieveFromTheServer() {

/*		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
				.create();

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setLog(new RestAdapter.Log() {
					@Override
					public void log(String message) {
						EventBus.getDefault().post(message);
					}
				})
				.setLogLevel(RestAdapter.LogLevel.BASIC)
				.setConverter(new GsonConverter(gson))
				.setEndpoint(Constant.API_URL)
				.build();

		// Create an instance of our GitHub API interface.
		ILogin markers = restAdapter.create(ILogin.class);

		markers.markers(new Callback<JSONObject[]>() {
			@Override
			public void success(JSONObject[] jsonObject, Response response) {
				Log.v(TAG, "Everything good.Num of markers: " + jsonObject.length);
				saveMarkersResponse(jsonObject);
				        *//*Add the MarkersOptions to the map*//*
				addMarkerOptions();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.v(TAG, "Tol failure: getResponse:" + error.getResponse() + "; error.getMessage:" + error.getMessage());
			}
		});*/
    }

    private void saveMarkersResponse(JSONObject[] jsonObject) {

		/*Reading the JSONArray from the server and saving it in the MarkersOptions array*/
        List<MarkerOptions> markersList = new ArrayList<>(jsonObject.length);
        for (int i = 0; i < jsonObject.length; i++) {
            JSONObject JSONmarker = jsonObject[i];
            MarkerOptions APPmarker = new MarkerOptions();
            try {
                APPmarker.title(JSONmarker.getString("title"))
                        .position(new LatLng(JSONmarker.getInt("latitude"), JSONmarker.getInt("longitude")))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            markersList.add(APPmarker);
        }
        this.mMarkerOptionsList = markersList;
    }

    private void addMarkerOptions() {

		/*This method add the markers to the map using the IMarker.addNewMarker method...*/
        List<Marker> markers = new ArrayList<>(this.mMarkerOptionsList.size());
        for (int i = 0; i < this.mMarkerOptionsList.size(); i++) {
            Marker marker = addNewMarker(this.mMarkerOptionsList.get(i));
            markers.add(marker);
        }

		/*...and then I added it to the UserProfile*/
        UserMarkers userMarkers = new UserMarkers(markers, UserProfile.getMainUserProfile().getUser_id());
        UserProfile.getMainUserProfile().setMarkers(userMarkers);
    }

    @Override
    public Marker addNewMarker(MarkerOptions pMarker) {

        return this.mMapView.addMarker(pMarker);
    }

    @Override
    public void removeOldMarker(MarkerOptions pMarker) {

    }

    public void onInfoClick() {
        Log.d(TAG, "Open the info of the user menu layout");
    }

    public void onAddClick() {
        Log.d(TAG, "Open the add marker menu layout");
    }
}
