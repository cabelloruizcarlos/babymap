package com.crrc.babymap.app.activities;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crrc.babymap.R;
import com.crrc.babymap.app.interfaces.ILogin;
import com.crrc.babymap.app.interfaces.IMarker;
import com.crrc.babymap.app.model.Constant;
import com.crrc.babymap.app.model.UserMarkers;
import com.crrc.babymap.app.model.UserProfile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class MapsActivity extends FragmentActivity implements IMarker {
	private String TAG = MapsActivity.class.getSimpleName();

	private GoogleMap mMap; // Might be null if Google Play services APK is not available.
	private List<MarkerOptions> mMarkerOptionsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		/*Create and set up the map*/
		setUpMapIfNeeded();

		/*Set the Maps to show the user position and do zoom in its current position*/
		if (!setMapsLocation())
			Toast.makeText(getApplicationContext(), getString(R.string.no_location), Toast.LENGTH_SHORT).show();

		this.mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
			@Override
			public boolean onMyLocationButtonClick() {
				if (!setMapsLocation())
					Toast.makeText(getApplicationContext(), getString(R.string.no_location), Toast.LENGTH_SHORT).show();

				return true;
			}
		});

		/*Show the zooming controls on the map*/
		this.mMap.getUiSettings().setZoomControlsEnabled(true);

	  /*Customization of the marker`s dialog*/
		this.mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				// Getting view from the layout file info_window_layout
				View v = getLayoutInflater().inflate(R.layout.activity_custom_info_window, null);

				// Returning the view containing InfoWindow contents
				return v;

			}
		});
		    /*Retrieve the data from the server, save it in the this.mMarkerOptionsList array and adding it into the maps*/
		retrieveFromTheServer();

	}

	private boolean setMapsLocation() {

		/*Show the location button*/
		this.mMap.setMyLocationEnabled(true);

		/*I get the location and I move the maps to this position adding a zoom*/
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
		if (location != null) {
			UserProfile.getMainUserProfile().setLastLatitude((float) location.getLatitude());
			UserProfile.getMainUserProfile().setLastLongitude((float) location.getLongitude());
			Log.v(TAG, "location.latitude: " + location.getLatitude() + ";location.longitude: " + location.getLongitude());
		} else if ((UserProfile.getMainUserProfile().getLastLatitude() == 0) || (UserProfile.getMainUserProfile().getLastLongitude() == 0)) {
			return false;
		}
		Log.v(TAG, "User.latitude: " + UserProfile.getMainUserProfile().getLastLatitude() + ";User.longitude:" + UserProfile.getMainUserProfile().getLastLongitude());
		this.setMapsZoom(UserProfile.getMainUserProfile().getLastLatitude(), UserProfile.getMainUserProfile().getLastLongitude());
		return true;
	}

	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
	 * installed) and the map has not already been instantiated..
	 * <p/>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
	 * install/update the Google Play services APK on their device.
	 * <p/>
	 * A user can return to this FragmentActivity after following the prompt and correctly
	 * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
	 * have been completely destroyed during this process (it is likely that it would only be
	 * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
	 * method in {@link #onResume()} to guarantee that it will be called.
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
		}
		this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

	public void setMapsZoom(double latitude, double longitude) {

		// Move the camera to last position with a zoom level
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(15).build();

		this.mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	private void retrieveFromTheServer() {

		Gson gson = new GsonBuilder()
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
				        /*Add the MarkersOptions to the map*/
				addMarkerOptions();
			}

			@Override
			public void failure(RetrofitError error) {
				Log.v(TAG, "Tol failure: getResponse:" + error.getResponse() + "; error.getMessage:" + error.getMessage());
			}
		});
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

		return this.mMap.addMarker(pMarker);
	}

	@Override
	public void removeOldMarker(MarkerOptions pMarker) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		setMapsLocation();
	}
}
