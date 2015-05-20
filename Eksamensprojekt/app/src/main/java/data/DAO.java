package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.LocalDateTime;

/**
 * Created by Henrik on 20-05-2015.
 */
public class DAO
{
	private static DAO instance;
	private Data datahelper;
	private SQLiteDatabase database;

	private DAO(Context context)
	{
		datahelper = new Data(context);
		database = datahelper.getWritableDatabase();
	}

	public static DAO getInstance(Context context)
	{
		if (instance == null)
			instance = new DAO(context);
		return instance;
	}

	public long opretKoersel(String adresseFra, String adresseTil, String regNr, LocalDateTime datoFra, LocalDateTime
			datoTil, double kmFra, double kmTil, int kmIAlt, String formaal)
	{
		ContentValues values = new ContentValues();
		values.put(Data.KOERSEL_ADRESSE_FRA, adresseFra);
		values.put(Data.KOERSEL_ADRESSE_TIL, adresseTil);
		values.put(Data.KOERSEL_REG_NR, regNr);
		values.put(Data.KOERSEL_DATO_FRA, datoFra.toString());
		values.put(Data.KOERSEL_DATO_TIL, datoTil.toString());
		values.put(Data.KOERSEL_KM_FRA, kmFra);
		values.put(Data.KOERSEL_KM_TIL, kmTil);
		values.put(Data.KOERSEL_KM_I_ALT, kmIAlt);
		values.put(Data.KOERSEL_FORMAAL, formaal);
		return database.insert(datahelper.KOERSLER_TABELNAVN, null, values);
	}

	public long opdaterKoersel(long id, String adresseFra, String adresseTil, String regNr, LocalDateTime datoFra,
							   LocalDateTime
			datoTil, double kmFra, double kmTil, int kmIAlt, String formaal)
	{
		String where = datahelper.KOERSEL_ID + " = ?";
		String[] args = new String[] { Long.toString(id) };

		ContentValues values = new ContentValues();
		values.put(Data.KOERSEL_ADRESSE_FRA, adresseFra);
		values.put(Data.KOERSEL_ADRESSE_TIL, adresseTil);
		values.put(Data.KOERSEL_REG_NR, regNr);
		values.put(Data.KOERSEL_DATO_FRA, datoFra.toString());
		values.put(Data.KOERSEL_DATO_TIL, datoTil.toString());
		values.put(Data.KOERSEL_KM_FRA, kmFra);
		values.put(Data.KOERSEL_KM_TIL, kmTil);
		values.put(Data.KOERSEL_KM_I_ALT, kmIAlt);
		values.put(Data.KOERSEL_FORMAAL, formaal);
		return database.update(datahelper.KOERSLER_TABELNAVN, values, where, args);
	}

	public int sletKorsel(long id)
	{
		String where = datahelper.KOERSEL_ID + " = ?";
		String[] args = new String[] { Long.toString(id) };
		return database.delete(datahelper.KOERSLER_TABELNAVN, where, args);
	}

	public int sletAlleKoersler()
	{
		return database.delete(datahelper.KOERSLER_TABELNAVN, null, null);
	}
}
