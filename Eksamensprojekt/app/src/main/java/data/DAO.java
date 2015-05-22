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
		return database.insert(datahelper.KOERSLER_TABELNAVN, null, values);
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

	public long opretAdresse(String adresse)
	{
		ContentValues values = new ContentValues();
		values.put(Data.ADRESSE, adresse);
		long adresseId = database.insert(Data.ADRESSE_TABELNAVN, null, values);
		// TODO debug. Why -1? Because it goes wrong, but why does it go wrong D:
		return adresseId;
	}

	public Cursor hentAlleAdresser()
	{
		return database.rawQuery("select * from " + Data.ADRESSE_TABELNAVN, null);
	}

	public String hentAdresse(int id)
	{
		String[] columns = new String[] { Data.ADRESSE };
		String where = Data.ADRESSE_ID + " = ?";
		String[] args = new String[] { Integer.toString(id) };
		Cursor cursor = database.query(Data.ADRESSE_TABELNAVN, columns, where, args, null, null, null);
		int adresseIndex = cursor.getColumnIndex(Data.ADRESSE);
		return cursor.getString(adresseIndex);
	}

	public void factoryReset()
	{
		database.rawQuery("delete * from " + Data.KOERSLER_TABELNAVN, null, null);
		database.rawQuery("delete * from " + Data.ADRESSE_TABELNAVN, null, null);
		// datahelper.onCreate(database);

		long sesamStreet = opretAdresse("Sesame Street");
		long disneyLand = opretAdresse("Disney Land");
		long adresse1 = opretAdresse("Adresse1");
		long adresse2 = opretAdresse("Adresse2");

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
