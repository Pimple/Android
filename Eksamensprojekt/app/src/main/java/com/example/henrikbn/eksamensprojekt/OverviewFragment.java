package com.example.henrikbn.eksamensprojekt;

import android.content.SharedPreferences;
import android.os.Bundle;
// import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.DAO;

public class OverviewFragment extends android.support.v4.app.Fragment
{
	public OverviewFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_overview, container, false);

		getActivity().setTitle(getText(R.string.title_section1));

		TextView brugernavnView = (TextView) view.findViewById(R.id.bruger_navn);
		TextView adresseView = (TextView) view.findViewById(R.id.bruger_adresse);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String navn = preferences.getString("brugernavn", "Brugernavn FEEJJJLL");
		long adresseId = preferences.getLong("brugerid", -1);
		String adresse = "Ingen adresse.";
		if (adresseId > -1 && !navn.equals("Brugernavn FEEJJJLL"))
			adresse = DAO.getInstance(getActivity()).hentAdresse(adresseId);

		brugernavnView.setText(navn);
		adresseView.setText(adresse);

		return view;
	}
}