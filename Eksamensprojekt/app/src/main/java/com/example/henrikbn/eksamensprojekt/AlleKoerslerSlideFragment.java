package com.example.henrikbn.eksamensprojekt;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import data.DAO;
import otherstuff.KoerselAdapter;
import otherstuff.KoerselPagerAdapter;

public class AlleKoerslerSlideFragment extends android.support.v4.app.Fragment
{
	public AlleKoerslerSlideFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_alle_koersler_slide, container, false);

		ViewPager koerslerPager = (ViewPager) view.findViewById(R.id.koersler_slide);
		Cursor koersler = DAO.getInstance(view.getContext()).hentAlleKoersler();
		PagerAdapter adapter = new KoerselPagerAdapter(view.getContext(), koersler);
		koerslerPager.setAdapter(adapter);

		return view;
	}
}