package nope.miniprojekt3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by HenrikBN on 10-04-2015.
 *
 * Jeg ved godt vi har en præsentation som siger det ikke er
 * nødvendigt at lukke databasen, men jeg er stædig :p
 */
public class TravelJournalDAO
{
	private DeepThought deepThought;
	private SQLiteDatabase database;

	private static TravelJournalDAO instance;

	private TravelJournalDAO(Context context)
	{
		deepThought = new DeepThought(context);
	}

	public static TravelJournalDAO getInstance(Context context)
	{
		if (instance == null)
			synchronized (TravelJournalDAO.class)
			{
				if (instance == null)
					instance = new TravelJournalDAO(context);
			}
		return instance;
	}

	private void open() throws SQLException
	{
		database = deepThought.getWritableDatabase();
	}

	private void close()
	{
		deepThought.close();
	}

	public TravelJournal createTravelJournal(int id, String title, String note,
			String address, String picture, String dateVisited, boolean visitAgain)
	{
		ContentValues values = new ContentValues();
		values.put(DeepThought.ID, id);
		values.put(DeepThought.TITLE, title);
		values.put(DeepThought.NOTE, note);
		values.put(DeepThought.ADDRESS, address);
		values.put(DeepThought.PICTURE, picture);
		values.put(DeepThought.DATE_VISITED, dateVisited);
		values.put(DeepThought.VISIT_AGAIN, visitAgain);

		long insertId = database.insert(DeepThought.TABLE, null,
				values);
		Cursor cursor = database.query(DeepThought.TABLE, DeepThought.ALL_COLUMNS,
				DeepThought.ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		cursor.close();

		TravelJournal newJournal = new TravelJournal(id, title, note, address, picture, dateVisited, visitAgain);
		return newJournal;
	}
}
