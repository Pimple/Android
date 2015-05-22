package com.example.henrikbn.eksamensprojekt;

import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import data.DAO;
import otherstuff.KoerselAdapter;

public class AlleKoerslerFragment extends android.support.v4.app.Fragment
{
	private DAO dao;

	public AlleKoerslerFragment()
	{
		dao = DAO.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_alle_koersler, container, false);

		ListView alleKoerslerList = (ListView) view.findViewById(R.id.alle_koersler_list);
		CursorAdapter adapter = new KoerselAdapter(getActivity(), dao.hentAlleKoersler());
		alleKoerslerList.setAdapter(adapter);

		return view;
	}
}
