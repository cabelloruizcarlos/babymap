package com.crrc.babymap.app.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.crrc.babymap.R;
import com.crrc.babymap.app.fragments.NavDrawerFragment;
import com.crrc.babymap.model.Constant;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Carlos on 07/04/2015.
 */
public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        this.mToolBar = (Toolbar) findViewById(R.id.app_bar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(this.mToolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawerLayout_fragment)).setUp(this.mDrawerLayout, this.mToolBar, R.id.drawerLayout_fragment);

/*
        // Create an instance of our GitHub API interface.
        ILogin markers = restAdapter.create(ILogin.class);

        markers.markers(new Callback<JSONObject[]>() {
            @Override
            public void success(JSONObject[] jsonObject, Response response) {
                saveMarkersResponse(jsonObject);
                Log.v(TAG, "Everything good.Num of markers: " + jsonObject.length);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(TAG, "Tol failure: getResponse:" + error.getResponse() + "; error.getMessage:" + error.getMessage());
            }
        });
*/
    }

    private void saveMarkersResponse(JSONObject[] jsonObject) {

//        Steps:
//        1 - From JSONArray to List<MarkerOptions. Adding each marker to the markerList
//        2 - With the user_id and the markerList, I create the UserMarkers and then I added it to the UserProfile
//        3 - Render again the maps view

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
    }
}
