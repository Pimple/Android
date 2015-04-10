package com.paad.earthquake;

import android.app.AlarmManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class EarthquakeListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	SimpleCursorAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Create a new Adapter and bind it to the List View
		adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null,
				new String[] { EarthquakeProvider.KEY_SUMMARY }, new int[] { android.R.id.text1 }, 0);
		setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);

		refreshEarthquakes();
	}
	
	@Override
	public void onDestroy() {
		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

		String ALARM_ACTION = EarthquakeAlarmReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM;
		Intent intentToFire = new Intent(ALARM_ACTION);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(), 0, intentToFire, 0);
		alarmManager.cancel(alarmIntent);
		super.onDestroy();
	}

	public void refreshEarthquakes() {
		getLoaderManager().restartLoader(0, null, EarthquakeListFragment.this);

		getActivity().startService(new Intent(getActivity(), EarthquakeUpdateService.class));
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = new String[] { EarthquakeProvider.KEY_ID, EarthquakeProvider.KEY_SUMMARY };

		Earthquake earthquakeActivity = (Earthquake) getActivity();
		String where = EarthquakeProvider.KEY_MAGNITUDE + " > "
				+ earthquakeActivity.getMinimumMagnitude();

		CursorLoader loader = new CursorLoader(getActivity(), EarthquakeProvider.CONTENT_URI,
				projection, where, null, null);

		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

}