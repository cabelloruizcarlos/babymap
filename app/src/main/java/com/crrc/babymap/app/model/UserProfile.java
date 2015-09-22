package com.crrc.babymap.app.model;

import android.content.SharedPreferences;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Carlos on 07/04/2015.
 */
public class UserProfile {

	private static SharedPreferences preferences;
	private static UserProfile mainUserProfile;

	private int user_id;
	private String user;
	private String pass;
	private UserMarkers markers;
	private boolean logged;
	private float lastLatitude;
	private float lastLongitude;

	/*Token generated by the server when the app ask for the credentials.
		Everytime that the user communicates with the server this token should be the security id*/
	private String userToken;

	public UserProfile() {
		this.lastLatitude = 0;
		this.lastLongitude = 0;
	}

	public static void initMainUserProfile(SharedPreferences p_preferences) {
		mainUserProfile = new UserProfile();
		setUserProfileData(p_preferences);
	}

	public static UserProfile getMainUserProfile() {
		return mainUserProfile;
	}

	public int getUser_id() {
		return getMainUserProfile().user_id;
	}

	public void setUser_id(int user_id) {
		SharedPreferences.Editor editor = preferences.edit();

		getMainUserProfile().user_id = user_id;
		editor.putInt("user_id", user_id);
	}

	public String getUser() {
		return getMainUserProfile().user;
	}

	public void setUser(String user) {
		SharedPreferences.Editor editor = preferences.edit();

		getMainUserProfile().user = user;
		editor.putString("user", user);
	}

	public String getPass() {
		return getMainUserProfile().pass;
	}

	public void setPass(String pass) {
		SharedPreferences.Editor editor = preferences.edit();

		getMainUserProfile().pass = pass;
		editor.putString("pass", pass);

		editor.commit();
	}

	public UserMarkers getMarkers() {
		return getMainUserProfile().markers;
	}

	public void setMarkers(UserMarkers markers) {
		getMainUserProfile().markers = markers;
	}

	public void addMarker(MarkerOptions pMarker) {
		getMainUserProfile().markers.addMarker(pMarker);
	}

	public void removeMarker(MarkerOptions pMarker) {
		getMainUserProfile().markers.removeMarker(pMarker);
	}

	public static boolean isLoggedOut() {
		return getMainUserProfile().logged;
	}

	public static void setLogged(boolean pLogged) {
		SharedPreferences.Editor editor = preferences.edit();

		getMainUserProfile().logged = pLogged;
		editor.putBoolean("logged", pLogged);
		editor.commit();
	}

	public float getLastLatitude() {
		return this.mainUserProfile.lastLatitude;
	}

	public void setLastLatitude(float lastLatitude) {
		SharedPreferences.Editor editor = preferences.edit();

		getMainUserProfile().lastLatitude = lastLatitude;
		editor.putFloat("latitude", lastLatitude);
		editor.commit();
	}

	public float getLastLongitude() {
		return this.mainUserProfile.lastLongitude;
	}

	public void setLastLongitude(float lastLongitude) {
		SharedPreferences.Editor editor = preferences.edit();

		getMainUserProfile().lastLongitude = lastLongitude;
		editor.putFloat("longitude", lastLongitude);
		editor.commit();
	}

	public static void setUserProfileData(SharedPreferences p_preferences) {

		getMainUserProfile().preferences = p_preferences;
		getMainUserProfile().user_id = p_preferences.getInt("user_id", 0);
		getMainUserProfile().user = p_preferences.getString("user", Constant.DEFAULT_USER);
		getMainUserProfile().pass = p_preferences.getString("password", "");
		getMainUserProfile().logged = p_preferences.getBoolean("logged", false);
		getMainUserProfile().lastLongitude = p_preferences.getFloat("longitude", 0);
		getMainUserProfile().lastLatitude = p_preferences.getFloat("latitude", 0);
		// TODO: Retrieve the list of markers from the database and save it in this.mainUserProfile.markers
	}

	public void removeUserProfileData() {
		SharedPreferences.Editor editor = preferences.edit();

		editor.remove("user_id");
		editor.remove("user");
		editor.remove("password");
		editor.remove("latitude");
		editor.remove("longitude");
		//TODO: Check how to remove the markers

		editor.commit();
	}

	public static boolean existAccountCached() {
		if (getMainUserProfile().user.equals(Constant.DEFAULT_USER)) {
			return false;
		}
		return true;
	}
}
