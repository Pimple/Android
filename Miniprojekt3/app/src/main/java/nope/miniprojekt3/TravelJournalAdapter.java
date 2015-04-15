package nope.miniprojekt3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Henrik on 15-04-2015.
 */
public class TravelJournalAdapter extends ArrayAdapter<TravelJournal>
{
	public TravelJournalAdapter(Context context, List<TravelJournal> distributors)
	{
		super(context, 0, distributors);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TravelJournal journal = getItem(position);

		if (convertView == null)
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.journal_view, parent, false);

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView date = (TextView) convertView.findViewById(R.id.date);
		TextView distance = (TextView) convertView.findViewById(R.id.distance);
		TextView description = (TextView) convertView.findViewById(R.id.description);

		title.setText(journal.getTitle());
		date.setText(journal.getDateVisited().toString());
		distance.setText(journal.getAddress()); // TODO
		description.setText(journal.getNote());

		ImageButton viewLocation = (ImageButton) convertView.findViewById(R.id.viewLocation);
		ImageButton viewPicture = (ImageButton) convertView.findViewById(R.id.viewPicture);

		viewLocation.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO
			}
		});

		viewPicture.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//  TODO
			}
		});

		ImageButton editJournal = (ImageButton) convertView.findViewById(R.id.editJournal);
		ImageButton removeJournal = (ImageButton) convertView.findViewById(R.id.removeJournal);

		editJournal.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//  TODO
			}
		});

		removeJournal.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//  TODO
			}
		});

		return convertView;
	}
}
