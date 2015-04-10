package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import henrikbn.projekt2015_02_18.R;
import model.Distributor;

/**
 * Created by HenrikBN on 19-02-2015.
 */
public class DistributorAdapter extends ArrayAdapter<Distributor>
{
	public DistributorAdapter(Context context, List<Distributor> distributors)
	{
		super(context, 0, distributors);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// Get the data item for this position
		Distributor distributor = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null)
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_distributors, parent, false);

		// Lookup view for data population
		TextView distributorName = (TextView) convertView.findViewById(R.id.listView_distributorName);

		// Populate the data into the template view using the data object
		distributorName.setText(distributor.getName());

		// Return the completed view to render on screen
		return convertView;
	}
}
