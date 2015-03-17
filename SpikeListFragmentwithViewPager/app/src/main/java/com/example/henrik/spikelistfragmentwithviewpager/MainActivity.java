package com.example.henrik.spikelistfragmentwithviewpager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity
{
	private static String INDEX = "index";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		TitlesFragment titles = new TitlesFragment();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment, titles);
		ft.commit();
	}

	private static int index = 0;

	public static class TitlesFragment extends ListFragment
	{
		private boolean landscape;

		@Override
		// da ListFragment har sin egen onCreate()
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (savedInstanceState != null) {
				index = savedInstanceState.getInt(INDEX, 0);
			}

			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_activated_1,
					Shakespeare.TITLES));
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			getListView().setItemChecked(index, true);

			View detailsFrame = getActivity().findViewById(R.id.details);
			landscape = detailsFrame != null;
			if (landscape) {
				getFragmentManager().popBackStack();
				showDetails();
			}
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt(INDEX, index);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			index = position;
			showDetails();
		}

		void showDetails() {
			DetailsFragment details = new DetailsFragment();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (landscape) {
				ft.replace(R.id.details, details);
			} else {
				ft.replace(R.id.fragment, details);
				ft.addToBackStack(null);
			}
			ft.commit();
		}
	}

	public static class DetailsFragment extends Fragment
	{

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (container == null)
				return null;
			View view = inflater.inflate(R.layout.fragment_details, container,
					false);
			return view;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			TextView textView = (TextView) getActivity()
					.findViewById(R.id.text);
			if (textView != null)
				textView.setText(Shakespeare.DIALOGUE[index]);
		}
	}

	/**
	 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
				case 0:
					return getString(R.string.title_section1).toUpperCase(l);
				case 1:
					return getString(R.string.title_section2).toUpperCase(l);
				case 2:
					return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends android.support.v4.app.Fragment
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		private int sectionNumber;

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			Bundle args = getArguments();
			sectionNumber = args.getInt(ARG_SECTION_NUMBER, 0);
			textView.setText(getText(R.string.fragment) + " " + sectionNumber);
			return rootView;
		}
	}
}
