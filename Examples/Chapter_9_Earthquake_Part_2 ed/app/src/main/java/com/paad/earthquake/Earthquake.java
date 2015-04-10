package com.paad.earthquake;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class Earthquake extends Activity {
	private static final int SHOW_PREFERENCES = 1;

	private int minimumMagnitude = 0;
	private boolean autoUpdateChecked = false;
	private int updateFreq = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		updateFromPreferences();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (R.id.menu_preferencess): {
			Intent i = new Intent(this, FragmentPreferences.class);
			startActivityForResult(i, SHOW_PREFERENCES);
			return true;
		}
		}
		return false;
	}

	private void updateFromPreferences() {
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		minimumMagnitude = Integer.parseInt(prefs.getString(UserPreferenceFragment.PREF_MIN_MAG, "3"));
		updateFreq = Integer.parseInt(prefs.getString(UserPreferenceFragment.PREF_UPDATE_FREQ, "1"));
		autoUpdateChecked = prefs.getBoolean(UserPreferenceFragment.PREF_AUTO_UPDATE, true);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == SHOW_PREFERENCES)
			updateFromPreferences();

		FragmentManager fm = getFragmentManager();
		EarthquakeListFragment earthquakeList = (EarthquakeListFragment) fm
				.findFragmentById(R.id.EarthquakeListFragment);
		earthquakeList.refreshEarthquakes();
	}

	public int getMinimumMagnitude() {
		return minimumMagnitude;
	}

	public boolean isAutoUpdateChecked() {
		return autoUpdateChecked;
	}

	public int getUpdateFreq() {
		return updateFreq;
	}

}