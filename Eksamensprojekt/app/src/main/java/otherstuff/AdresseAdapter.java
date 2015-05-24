package otherstuff;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henrikbn.eksamensprojekt.R;

import data.DAO;
import data.Data;

/**
 * Created by Henrik on 23-05-2015.
 */
public class AdresseAdapter extends CursorAdapter
{
	public AdresseAdapter(Context context, Cursor cursor)
	{
		super(context, cursor, 0);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.adresse_view, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		long id = cursor.getLong(cursor.getColumnIndex(Data.ADRESSE_ID));
		String adresse = cursor.getString(cursor.getColumnIndex(Data.ADRESSE));
		boolean privat = cursor.getInt(cursor.getColumnIndex(Data.ADRESSE_PRIVAT)) != 0;

		TextView idView = (TextView) view.findViewById(R.id.adresse_id);
		TextView adresseView = (TextView) view.findViewById(R.id.adresse);
		TextView privatView = (TextView) view.findViewById(R.id.adresse_privat);

		idView.setText(id + "");
		adresseView.setText(adresse);

		if (privat)
			privatView.setText("P");
		else
			privatView.setText("E");
	}
}
