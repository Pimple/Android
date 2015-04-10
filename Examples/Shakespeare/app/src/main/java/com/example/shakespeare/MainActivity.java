package com.example.shakespeare;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
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

	public static class TitlesFragment extends ListFragment {
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

	public static class DetailsFragment extends Fragment {

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
}
