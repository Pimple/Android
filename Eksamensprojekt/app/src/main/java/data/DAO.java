package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	public long opretKoersel(long adresseFra, long adresseTil, String regNr, LocalDateTime datoFra, LocalDateTime
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
		return database.insert(Data.KOERSLER_TABELNAVN, null, values);
	}

	public long opdaterKoersel(long id, long adresseFra, long adresseTil, String regNr, LocalDateTime datoFra,
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
		return database.update(Data.KOERSLER_TABELNAVN, values, where, args);
	}

	public int sletKorsel(long id)
	{
		String where = Data.KOERSEL_ID + " = ?";
		String[] args = new String[] { Long.toString(id) };
		return database.delete(Data.KOERSLER_TABELNAVN, where, args);
	}

	public int sletAlleKoersler()
	{
		return database.delete(Data.KOERSLER_TABELNAVN, null, null);
	}

	public Cursor hentAlleKoersler()
	{
		return database.rawQuery("select * from " + Data.KOERSLER_TABELNAVN, null);
	}

	public long opretAdresse(String adresse, boolean privat)
	{
		// int privatAsInt = (privat)? 1 : 0;

		ContentValues values = new ContentValues();
		values.put(Data.ADRESSE, adresse);
		values.put(Data.ADRESSE_PRIVAT, privat);
		long adresseId = database.insert(Data.ADRESSE_TABELNAVN, null, values);
		return adresseId;
	}

	public Cursor hentAlleAdresser()
	{
		return database.rawQuery("select * from " + Data.ADRESSE_TABELNAVN, null);
	}

	public String hentAdresse(long id)
	{
		Cursor cursor = database.query(Data.ADRESSE_TABELNAVN, Data.ADRESSE_COLUMNS, Data.ADRESSE_ID + " = " + id,
				null, null, null, null);
		if (cursor.getCount() < 1)
			return null;

		cursor.moveToFirst();
		int adresseIndex = cursor.getColumnIndex(Data.ADRESSE);
		String adresse = cursor.getString(adresseIndex);

		int privatIndex = cursor.getColumnIndex(Data.ADRESSE_PRIVAT);
		boolean privat = cursor.getInt(privatIndex) != 0;

		if (privat)
			adresse = "(P) " + adresse;
		else
			adresse = "(E) " + adresse;

		cursor.close();
		return adresse;
	}

	public void factoryReset()
	{
		database.delete(Data.KOERSLER_TABELNAVN, null, null);
		database.delete(Data.ADRESSE_TABELNAVN, null, null);
		// datahelper.onCreate(database);

		long sesamStreet = opretAdresse("Sesame Street", true);
		long disneyLand = opretAdresse("Disney Land", false);
		long adresse1 = opretAdresse("Adresse1", false);
		long adresse2 = opretAdresse("Adresse2", false);

		opretKoersel(adresse1, adresse2, "H3RP4D3RP", new LocalDateTime(2014, 6, 12, 15, 42),
				new LocalDateTime(2014, 6, 12, 16, 15), 0.0, 100.5, 101, "Vigtigt vigtigt møde.");
		opretKoersel(adresse2, adresse1, "H3RP4D3RP", new LocalDateTime(2014, 6, 12, 15, 42),
				new LocalDateTime(2014, 6, 12, 17, 00), 100.5, 201.0, 101, "Glemte min madpakke.");
		opretKoersel(adresse1, adresse2, "H3RP4D3RP", new LocalDateTime(2014, 6, 12, 15, 42),
				new LocalDateTime(2014, 6, 12, 18, 00), 201.0, 301.5, 101, "Vigtigt vigtigt møde.");
		opretKoersel(sesamStreet, disneyLand, "1234567", new LocalDateTime(2015, 6, 12, 15, 42),
				new LocalDateTime(2015, 6, 12, 16, 15), 301.5, 9301.5, 9000, "Fusk.");
	}
}
