package com.example.henrikbn.eksamensprojekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import data.DAO;
import data.Data;
import otherstuff.AdresseAdapter;

public class BrugerFragment extends android.support.v4.app.DialogFragment
{
	public BrugerFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getDialog().setTitle(getString(R.string.bruger_dialog_titel));

		View view = inflater.inflate(R.layout.fragment_bruger, container, false);

		final TextView navnView = (TextView) view.findViewById(R.id.brugerNavn);
		Spinner adresseView = (Spinner) view.findViewById(R.id.brugerAdresse);
		final Cursor adresser = DAO.getInstance(getActivity()).hentAlleAdresser();
		CursorAdapter adapter = new AdresseAdapter(getActivity(), adresser);
		adresseView.setAdapter(adapter);

		Button justerBrugerButton = (Button) view.findViewById(R.id.juster_bruger_button);
		justerBrugerButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String navn = navnView.getText().toString();
				long adresse = adresser.getLong(adresser.getColumnIndex(Data.ADRESSE_ID));

				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("brugernavn", navn);
				editor.putLong("brugerid", adresse);
				editor.commit();
				Toast toast = Toast.makeText(getActivity(), getText(R.string.toast_opdateret_bruger),
						Toast.LENGTH_SHORT);
				toast.show();
				getDialog().dismiss();
			}
		});

		return view;
	}
}