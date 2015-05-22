package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Henrik on 19-05-2015.
 */
public class Data extends SQLiteOpenHelper
{
	private static final String SKEMA_FIL = "skema.db";
	private static final int VERSION = 2;

	public static final String KOERSEL_ID = "_id";
	public static final String KOERSEL_ADRESSE_FRA = "adresse_fra";
	public static final String KOERSEL_ADRESSE_TIL = "adresse_til";
	public static final String KOERSEL_REG_NR = "reg_nr";
	public static final String KOERSEL_DATO_FRA = "dato_fra";
	public static final String KOERSEL_DATO_TIL = "dato_til";
	public static final String KOERSEL_KM_FRA = "km_fra";
	public static final String KOERSEL_KM_TIL = "km_til";
	public static final String KOERSEL_KM_I_ALT = "km_i_alt";
	public static final String KOERSEL_FORMAAL = "formaal";

	public static final String[] ALL_COLUMNS = { KOERSEL_ID, KOERSEL_ADRESSE_FRA, KOERSEL_ADRESSE_TIL, KOERSEL_REG_NR,
			KOERSEL_DATO_FRA, KOERSEL_DATO_TIL, KOERSEL_KM_FRA,	KOERSEL_KM_TIL, KOERSEL_KM_I_ALT, KOERSEL_FORMAAL };

	public static final String KOERSLER_TABELNAVN = "koersler";

	public static final String OPRET_KOERSLER =
			"create table if not exists " + KOERSLER_TABELNAVN +
			"(" +
					KOERSEL_ID + " integer primary key autoincrement, " +
					KOERSEL_ADRESSE_FRA + " int not null, " +
					KOERSEL_ADRESSE_TIL + " int not null, " +
					KOERSEL_REG_NR + " text not null, " +
					KOERSEL_DATO_FRA + " text not null, " +
					KOERSEL_DATO_TIL + " text not null, " +
					KOERSEL_KM_FRA + " double null, " +
					KOERSEL_KM_TIL + " double null, " +
					KOERSEL_KM_I_ALT + " int null, " +
					KOERSEL_FORMAAL + " text not null" +
			");";

	public static final String ADRESSE_ID = "_id";
	public static final String ADRESSE = "adresse";

	public static final String[] ADRESSE_COLUMNS = { ADRESSE_ID, ADRESSE };
	public static final String ADRESSE_TABELNAVN = "adresser";

	public static final String OPRET_ADRESSER =
			"create table if not exists " + ADRESSE_TABELNAVN +
			"(" +
					ADRESSE_ID + " integer primary key autoincrement, " +
					ADRESSE + " text not null" +
			");";

	public Data(Context context)
	{
		super(context, SKEMA_FIL, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(OPRET_KOERSLER);
		database.execSQL(OPRET_ADRESSER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(Data.class.getName(), "Opgraderede databasen fra version " + oldVersion
				+ " til " + newVersion + ". Held og lykke.");
		database.execSQL("drop table if exists " + KOERSLER_TABELNAVN);
		database.execSQL("drop table if exists " + ADRESSE_TABELNAVN);
		onCreate(database);
	}
}
