package com.crrc.babymap.app.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.crrc.babymap.R;
import com.crrc.babymap.app.interfaces.IMapsView;
import com.crrc.babymap.app.presenters.MapsPresenter;
import com.crrc.babymap.app.model.Constant;
import com.crrc.babymap.app.model.UserProfile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MapsActivity extends FragmentActivity implements View.OnClickListener, IMapsView {
	private String TAG = MapsActivity.class.getSimpleName();

	private GoogleMap mMap; // Might be null if Google Play services APK is not available.
	private Location mLocation;

	private MapsPresenter mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		this.mPresenter = new MapsPresenter(this);
        this.mPresenter.initView();
	}

	public void loadMenu() {

		ImageView icon = new ImageView(this);
		ImageView subIcon = new ImageView(this);
		ImageView subIcon2 = new ImageView(this);

		icon.setImageResource(R.mipmap.ic_launcher);
		subIcon.setImageDrawable(getResources().getDrawable(R.drawable.subaction_button_1));
		subIcon2.setImageDrawable(getResources().getDrawable(R.drawable.subaction_button_2));

		FloatingActionButton menuBtn = new FloatingActionButton.Builder(this)
				.setBackgroundDrawable(R.drawable.selector_menu_button)
				.build();

		SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
		SubActionButton infoBtn = itemBuilder.setContentView(subIcon).build();
		SubActionButton addMarkerBtn = itemBuilder.setContentView(subIcon2).build();

		infoBtn.setTag(Constant.INFO_MENU_BTN_TAG);
		addMarkerBtn.setTag(Constant.ADD_MENU_BTN_TAG);

		infoBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_menu_info_button));
		addMarkerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_menu_info_button));

		FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(infoBtn)
				.addSubActionView(addMarkerBtn)
				.attachTo(menuBtn)
				.build();
	}

    @Override
    public GoogleMap getMapView() {
        return this.mMap;
    }

    public Activity getMapsActivity(){
        return this;
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

    public void showMapsActualLocation(){

        /*Set the Maps to show the user position and do zoom in its current position*/
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

			/*I need to implement the new MArshmallow permissions*/
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
				/*return TODO;*/
            }
        } else {
			/*We get the location and then set the zoom*/
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            this.mLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            setMapsLocation();
        }
        if (!setMapsLocation())
            Toast.makeText(getApplicationContext(), getString(R.string.no_location), Toast.LENGTH_LONG).show();

        this.mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (!setMapsLocation())
                    Toast.makeText(getApplicationContext(), getString(R.string.no_location), Toast.LENGTH_LONG).show();

                return true;
            }
        });
    }

    private boolean setMapsLocation() {

		/*Show the location button*/
		this.mMap.setMyLocationEnabled(true);

		/*I move the map to this position adding a zoom*/
		if (this.mLocation != null) {
			UserProfile.getMainUserProfile().setLastLatitude((float) this.mLocation.getLatitude());
			UserProfile.getMainUserProfile().setLastLongitude((float) this.mLocation.getLongitude());
			Log.v(TAG, "location.latitude: " + this.mLocation.getLatitude() + ";location.longitude: " + this.mLocation.getLongitude());
		} else if ((UserProfile.getMainUserProfile().getLastLatitude() == 0) || (UserProfile.getMainUserProfile().getLastLongitude() == 0)) {
			return false;
		}
		Log.v(TAG, "User.latitude: " + UserProfile.getMainUserProfile().getLastLatitude() + ";User.longitude:" + UserProfile.getMainUserProfile().getLastLongitude());
		this.setMapsZoom(UserProfile.getMainUserProfile().getLastLatitude(), UserProfile.getMainUserProfile().getLastLongitude());
		return true;
	}

	public void setMapsZoom(double latitude, double longitude) {

		// Move the camera to last position with a zoom level
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(15).build();

		this.mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
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
	public void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
		}
		this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

	@Override
	public void onClick(View v) {

		String tag = (String) v.getTag();
		switch (tag) {
			case Constant.INFO_MENU_BTN_TAG: {
				mPresenter.onInfoClick();
				break;
			}
			case Constant.ADD_MENU_BTN_TAG: {
				mPresenter.onAddClick();
				break;
			}
		}
	}
}
