package com.example.henrikbn.eksamensprojekt;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import data.DAO;

public class MainActivity extends ActionBarActivity	implements NavigationDrawerFragment.NavigationDrawerCallbacks
{
	private DAO dao;

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dao = DAO.getInstance(getApplicationContext());

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment nytIndhold = null;
		switch (position)
		{
			case 0:
				nytIndhold = new OverviewFragment();
				break;
			case 1:
				nytIndhold = new AlleKoerslerFragment();
				break;
			case 2:
				nytIndhold = new AlleKoerslerSlideFragment();
				break;
			case 3:
				nytIndhold = new BrugerFragment();
				break;
			case 4:
				nytIndhold = new NyKoerselFragment();
				break;
			case 5:
				// nytIndhold = new NyAdresseFragment();
				break;
			case 6:
				// nytIndhold = new MailFragment();
				break;
		}
		if (nytIndhold == null)
		{
			Toast toast = Toast.makeText(getApplicationContext(), R.string.error_loading_content, Toast.LENGTH_LONG);
			toast.show();
		}
		else
		{
			fragmentManager.beginTransaction()
					.replace(R.id.container, nytIndhold)
					.commit();
		}
	}

	public void onSectionAttached(int number)
	{
		switch (number)
		{
			case 1:
				mTitle = getString(R.string.title_section1);
				break;
			case 2:
				mTitle = getString(R.string.title_section2);
				break;
			case 3:
				mTitle = getString(R.string.title_section3);
				break;
			case 4:
				mTitle = getString(R.string.title_section4);
			case 5:
				mTitle = getString(R.string.title_section5);
			case 6:
				mTitle = getString(R.string.title_section6);
			case 7:
				mTitle = getString(R.string.title_section7);
		}
	}

	public void restoreActionBar()
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if (!mNavigationDrawerFragment.isDrawerOpen())
		{
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.action_settings)
		{
			return true;
		}
		else if (id == R.id.action_juster_bruger)
		{
			// FragmentManager fm = getSupportFragmentManager();
			// BrugerFragment brugerFragment = new BrugerFragment();
			// brugerFragment.show(fm, "bruger_dialog");
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public static class MenuItemFragment extends Fragment
	{
		private static final String ARG_SECTION_NUMBER = "section_number";

		public static MenuItemFragment newInstance(int sectionNumber)
		{
			MenuItemFragment fragment = new MenuItemFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public MenuItemFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity)
		{
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(
					getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

}
