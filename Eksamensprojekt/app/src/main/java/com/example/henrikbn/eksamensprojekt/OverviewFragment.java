package com.example.henrikbn.eksamensprojekt;

import android.os.Bundle;
// import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OverviewFragment extends android.support.v4.app.Fragment
{
	public OverviewFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_overview, container, false);
	}
}