package nope.miniprojekt3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HenrikBN on 10-04-2015.
 */
public class DeepThought extends SQLiteOpenHelper
{
	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String NOTE = "note";
	public static final String ADDRESS = "address";
	public static final String PICTURE = "picture";
	public static final String DATE_VISITED = "date_visited";
	public static final String VISIT_AGAIN = "visit_again";

	public static final String[] ALL_COLUMNS = { ID, TITLE, NOTE, ADDRESS, PICTURE, DATE_VISITED, VISIT_AGAIN };

	public static final String TABLE = "travel_journal";

	private static final String SCHEME_FILE = "nope.db";
	private static final int VERSION = 1;

	private static final String CREATE_DATABASE =
			"create table " + TABLE +
			"(" +
				ID + " integer primary key autoincrement, " +
				TITLE + " text not null, " +
				NOTE + " text not null, " +
				ADDRESS + " text not null, " +
				PICTURE + " text null, " +
				DATE_VISITED + " text not null, " +
				VISIT_AGAIN + " boolean not null check(" + VISIT_AGAIN + " in (0,1)" +
			");";

	public DeepThought(Context context)
	{
		super(context, SCHEME_FILE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(CREATE_DATABASE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(DeepThought.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ". Good luck.");
		database.execSQL("drop table if exists " + TABLE);
		onCreate(database);
	}
}
