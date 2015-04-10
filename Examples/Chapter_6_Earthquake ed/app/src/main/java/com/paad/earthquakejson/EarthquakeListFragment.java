package com.paad.earthquakejson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListFragment;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EarthquakeListFragment extends ListFragment {

	private ArrayAdapter<Quake> aa;
	private ArrayList<Quake> earthquakes = new ArrayList<Quake>();
	private Handler handler = new Handler();
	
	private Date date = null;
	private String details = null;
	private Location location = null;
	private double magnitude = 0;
	private String link = null;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		int layoutID = android.R.layout.simple_list_item_1;
		aa = new ArrayAdapter<Quake>(getActivity(), layoutID, earthquakes);
		setListAdapter(aa);

		new Thread(new Runnable() {
			public void run() {
//				refreshEarthquakes();
				refreshEarthquakes2();
			}
		}).start();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Quake quake = (Quake) l.getSelectedItem();
		Uri.Builder geoLocation = new Uri.Builder();
	}

	public void showMap(Uri geoLocation)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(geoLocation);
		if (intent.resolveActivity(getActivity().getPackageManager()) != null)
			startActivity(intent);
	}

	private void refreshEarthquakes2() {
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) new URL(
					getString(R.string.quake_feed)).openConnection();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				earthquakes.clear();
				InputStream in = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				String line;
				StringBuilder builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				readEarthquakes2(builder.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private void readEarthquakes2(String string) {
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
				
				final Quake quake = new Quake(date, details, location, magnitude, link);

				// Process a newly found earthquake
				handler.post(new Runnable() {
					public void run() {
						addNewQuake(quake);
					}
				});
			}
		} catch (Exception e) {
			Log.d(null, e.getMessage());
		}
	}

	private void refreshEarthquakes() {
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) new URL(
					getString(R.string.quake_feed)).openConnection();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				earthquakes.clear();
				InputStream in = httpConnection.getInputStream();
				JsonReader reader = new JsonReader(new InputStreamReader(in,
						"UTF-8"));
				try {
					readEarthquakes(reader);
				} finally {
					reader.close();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private void readEarthquakes(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String s = reader.nextName();
			if (s.equals("features")) {
				reader.beginArray();
				while (reader.hasNext()) {
					readEarthquake(reader);
				}
				reader.endArray();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	private void readEarthquake(JsonReader reader) throws IOException {
		String s;
		reader.beginObject();
		while (reader.hasNext()) {
			s = reader.nextName();
			if (s.equals("properties")) {
				readProperties(reader);
			} else if (s.equals("geometry")) {
				readGeometry(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

		final Quake quake = new Quake(date, details, location, magnitude, link);

		// Process a newly found earthquake
		handler.post(new Runnable() {
			public void run() {
				addNewQuake(quake);
			}
		});
	}

	private void addNewQuake(Quake _quake) {
		earthquakes.add(_quake);
		aa.notifyDataSetChanged();
	}

	private void readProperties(JsonReader reader) throws IOException {
		String s;
		reader.beginObject();
		while (reader.hasNext()) {
			s = reader.nextName();
			if (s.equals("mag")) {
				magnitude = reader.nextDouble();
			} else if (s.equals("place")) {
				details = reader.nextString();
			} else if (s.equals("detail")) {
				link = reader.nextString();
			} else if (s.equals("time")) {
				date = new Date(Long.parseLong(reader.nextString()));
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	private void readGeometry(JsonReader reader) throws IOException {
		String s;
		reader.beginObject();
		while (reader.hasNext()) {
			s = reader.nextName();
			if (s.equals("coordinates")) {
				reader.beginArray();
				location = new Location("http://earthquake.usgs.gov/");
				location.setLongitude(reader.nextDouble());
				location.setLatitude(reader.nextDouble());
				location.setAltitude(-reader.nextDouble());
				reader.endArray();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}
}