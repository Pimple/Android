package com.example.henrikbn.eksamensprojekt;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.LocalDateTime;

import data.DAO;
import data.Data;
import otherstuff.AdresseAdapter;

public class NyKoerselFragment extends android.support.v4.app.Fragment
{
	public NyKoerselFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		final Spinner adresseFra, adresseTil;
		final EditText datoFra, datoTil, tidFra, tidTil, regNr, kmFra, kmTil, formaal;
		final Button opretKoersel;

		final DAO dao = DAO.getInstance(getActivity());

		View view = inflater.inflate(R.layout.fragment_ny_koersel, container, false);

		adresseFra = (Spinner) view.findViewById(R.id.adresse_fra);
		adresseTil = (Spinner) view.findViewById(R.id.adresse_til);

		final Cursor adresserFra = dao.hentAlleAdresser();
		final Cursor adresserTil = dao.hentAlleAdresser();

		CursorAdapter adapterFra = new AdresseAdapter(view.getContext(), adresserFra);
		CursorAdapter adapterTil = new AdresseAdapter(view.getContext(), adresserTil);
		adresseFra.setAdapter(adapterFra);
		adresseTil.setAdapter(adapterTil);

		datoFra = (EditText) view.findViewById(R.id.fra_dato);
		datoTil = (EditText) view.findViewById(R.id.til_dato);
		tidFra = (EditText) view.findViewById(R.id.fra_tid);
		tidTil = (EditText) view.findViewById(R.id.til_tid);

		LocalDateTime nu = new LocalDateTime();

		String dato = formatNumber(nu.getYear(), 4) + "-" + formatNumber(nu.getMonthOfYear(), 2) + "-" +
				formatNumber(nu.getDayOfMonth(), 2);

		datoFra.setText(dato);
		datoTil.setText(dato);

		String tid = formatNumber(nu.getHourOfDay(), 2) + ":" + formatNumber(nu.getMinuteOfHour(), 2) + ".000";
		String tid2 = formatNumber(nu.getHourOfDay() + 1, 2) + ":" + formatNumber(nu.getMinuteOfHour(), 2) + ".000";

		tidFra.setText(tid);
		tidTil.setText(tid2);

		regNr = (EditText) view.findViewById(R.id.reg_nr);

		kmFra = (EditText) view.findViewById(R.id.km_fra);
		kmTil = (EditText) view.findViewById(R.id.km_til);

		kmFra.setText("0.0");
		kmTil.setText("0.0");

		formaal = (EditText) view.findViewById(R.id.formaal);

		opretKoersel = (Button) view.findViewById(R.id.opret_koersel);

		opretKoersel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				long nyAdresseFraId = adresserFra.getLong(adresserFra.getColumnIndex(Data.ADRESSE_ID));
				long nyAdresseTilId = adresserTil.getLong(adresserTil.getColumnIndex(Data.ADRESSE_ID));
				LocalDateTime nyDatoFra = new LocalDateTime(datoFra.getText() + "T" + tidFra.getText());
				LocalDateTime nyDatoTil = new LocalDateTime(datoTil.getText() + "T" + tidTil.getText());
				String nyRegNr = regNr.getText().toString();
				double nyKmFra = Double.parseDouble(kmFra.getText().toString());
				double nyKmTil = Double.parseDouble(kmTil.getText().toString());
				int nyKmIAlt = (int) (nyKmTil - nyKmFra);
				String nyFormaal = formaal.getText().toString();

				dao.opretKoersel(nyAdresseFraId, nyAdresseTilId, nyRegNr, nyDatoFra, nyDatoTil, nyKmFra, nyKmTil,
						nyKmIAlt, nyFormaal);

				Toast.makeText(v.getContext(), getText(R.string.toast_oprettet_koersel), Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}

	private String formatNumber(int value, int digits)
	{
		String streng = value + "";
		if (streng.length() < digits)
			streng = "0" + streng;
		return streng;
	}
}