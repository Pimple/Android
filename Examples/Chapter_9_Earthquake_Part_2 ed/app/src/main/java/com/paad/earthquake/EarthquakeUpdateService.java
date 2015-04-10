package com.paad.earthquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class EarthquakeUpdateService extends Service {
	// Chapter_9_Earthquake_Part_2 ed

	private AlarmManager alarmManager;
	private PendingIntent alarmIntent;

	private Date date = null;
	private String details = null;
	private Location location = null;
	private double magnitude = 0;
	private String link = null;

	@Override
	public void onCreate() {
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		String ALARM_ACTION = EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM;
		Intent intentToFire = new Intent(ALARM_ACTION);
		alarmIntent = PendingIntent.getBroadcast(this, 0, intentToFire, 0);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Retrieve the shared preferences
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		final int updateFreq = Integer.parseInt(prefs.getString(UserPreferenceFragment.PREF_UPDATE_FREQ, "1"));
		boolean autoUpdateChecked = prefs.getBoolean(UserPreferenceFragment.PREF_AUTO_UPDATE, true);

		if (autoUpdateChecked) {
			int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
			long timeToRefresh = SystemClock.elapsedRealtime() + updateFreq * 60 * 1000;
			alarmManager.setInexactRepeating(alarmType, timeToRefresh, updateFreq * 60 * 1000, alarmIntent);
		} else
			alarmManager.cancel(alarmIntent);

		Thread t = new Thread(new Runnable() {
			public void run() {
				refreshEarthquakes();
				Log.d(null, "refreshEarthquakes");
			}
		});
		t.start();

		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		alarmManager.cancel(alarmIntent);
		super.onDestroy();
	}

	public void refreshEarthquakes() {
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) new URL(getString(R.string.quake_feed))
					.openConnection();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line;
				StringBuilder builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				readEarthquakes(builder.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			stopSelf();
		}
	}

	private void readEarthquakes(String string) {
		try {
			JSONObject geoJSON = new JSONObject(string);
			JSONArray features = geoJSON.getJSONArray("features");
			for (int i = 0; i < features.length(); i++) {
				JSONObject earthquake = features.getJSONObject(i);
				JSONObject properties = earthquake.getJSONObject("properties");
				JSONObject geometry = earthquake.getJSONObject("geometry");
				JSONArray coordinates = geometry.getJSONArray("coordinates");

				magnitude = properties.getDouble("mag");
				details = properties.getString("place");
				link = properties.getString("detail");
				date = new Date(properties.getLong("time"));
				location = new Location("http://earthquake.usgs.gov/");
				location.setLongitude(coordinates.getDouble(0));
				location.setLatitude(coordinates.getDouble(1));
				location.setAltitude(-coordinates.getDouble(2));

				Quake quake = new Quake(date, details, location, magnitude, link);

				addNewQuake(quake);
			}
		} catch (Exception e) {
			Log.d(null, e.getMessage());
		}
	}

	private void addNewQuake(Quake quake) {
		ContentResolver cr = getContentResolver();

		// Construct a where clause to make sure we don't already have this
		// earthquake in the provider.
		String where = EarthquakeProvider.KEY_DATE + " = " + quake.getDate().getTime();

		// If the earthquake is new, insert it into the provider.
		Cursor query = cr.query(EarthquakeProvider.CONTENT_URI, null, where, null, null);

		if (query.getCount() == 0) {
			ContentValues values = new ContentValues();

			values.put(EarthquakeProvider.KEY_DATE, quake.getDate().getTime());
			values.put(EarthquakeProvider.KEY_DETAILS, quake.getDetails());
			values.put(EarthquakeProvider.KEY_SUMMARY, quake.toString());

			double lat = quake.getLocation().getLatitude();
			double lng = quake.getLocation().getLongitude();
			values.put(EarthquakeProvider.KEY_LOCATION_LAT, lat);
			values.put(EarthquakeProvider.KEY_LOCATION_LNG, lng);
			values.put(EarthquakeProvider.KEY_LINK, quake.getLink());
			values.put(EarthquakeProvider.KEY_MAGNITUDE, quake.getMagnitude());

			cr.insert(EarthquakeProvider.CONTENT_URI, values);
		}
		query.close();
	}
}