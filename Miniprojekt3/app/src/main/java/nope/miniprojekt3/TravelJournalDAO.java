package nope.miniprojekt3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelJournalDAO
{
	private DeepThought deepThought;
	private SQLiteDatabase database;

	private static TravelJournalDAO instance;

	private TravelJournalDAO(Context context)
	{
		deepThought = new DeepThought(context);
	}

	public static TravelJournalDAO getInstance (Context context)
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

	public TravelJournal createTravelJournal (String title, String note,
			String address, String picture, String dateVisited, boolean visitAgain)
	{
		TravelJournal newJournal = null;
		try
		{
			open();
			ContentValues newValues = new ContentValues();
			newValues.put(DeepThought.TITLE, title);
			newValues.put(DeepThought.NOTE, note);
			newValues.put(DeepThought.ADDRESS, address);
			newValues.put(DeepThought.PICTURE, picture);
			newValues.put(DeepThought.DATE_VISITED, dateVisited);
			newValues.put(DeepThought.VISIT_AGAIN, visitAgain);
			long newId = database.insert(DeepThought.TABLE, null, newValues);

			Cursor newRow = database.query(DeepThought.TABLE, DeepThought.ALL_COLUMNS, DeepThought.ID
					+ " = " + newId, null, null, null, null);
			newRow.moveToFirst();
			newRow.close();

			newJournal = new TravelJournal((int) newId, title, note, address, picture, dateVisited,
					visitAgain);
		}
		catch (SQLException ex)
		{
			Log.w(TravelJournalDAO.class.getName(), "Failed to create TravelJournal cause of SQL and things.");
			ex.printStackTrace();
		}
		finally
		{
			if (database != null && database.isOpen())
				close();
		}
		return newJournal;
	}

	public List<TravelJournal> getAllTravelJournals()
	{
		List<TravelJournal> journals = new ArrayList<>();
		try
		{
			open();

			Cursor cursor = database.query(DeepThought.TABLE, DeepThought.ALL_COLUMNS, null, null, null, null, null);
			cursor.moveToFirst();

			while (!cursor.isAfterLast())
			{
				int id = cursor.getInt(0);
				String title = cursor.getString(1);
				String note = cursor.getString(2);
				String address = cursor.getString(3);
				String picture = cursor.getString(4);
				String dateVisited = cursor.getString(5);
				boolean visitAgain = cursor.getInt(6) == 1;

				TravelJournal newJournal = new TravelJournal(id, title, note, address, picture, dateVisited, visitAgain);
				journals.add(newJournal);
			}
		}
		catch (SQLException ex)
		{
			Log.w(TravelJournalDAO.class.getName(), "Failed to fetch all TravelJournals cause of SQL and things.");
			ex.printStackTrace();
		}
		finally
		{
			if (database != null && database.isOpen())
				close();
		}
		return journals;
	}

	private TravelJournal createObjectFromCursor (Cursor cursor)
	{
		int id = cursor.getInt(0);
		String title = cursor.getString(1);
		String note = cursor.getString(2);
		String address = cursor.getString(3);
		String picture = cursor.getString(4);
		String dateVisited = cursor.getString(5);
		boolean visitAgain = cursor.getInt(6) == 1;

		TravelJournal newJournal = new TravelJournal(id, title, note, address, picture, dateVisited, visitAgain);
		return newJournal;
	}
}
