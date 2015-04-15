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
		database = deepThought.getWritableDatabase();
		populate();
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

	private void populate()
	{
		String sql = "SELECT count(*) FROM " + DeepThought.TABLE;
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		if(count == 0)
		{
			createTravelJournal("Bingo", "Det var godt nok sjov`!", "Østervold 1, 8900 Randers, Danmark", null,
					"2015-03-15", true);
			createTravelJournal("Læst en bog \"i det fri\"", "Endnu en dag. Endnu et eventyr. Og " +
							"bogen var ikke helt dårlig. Kun næsten.","Jernbanegade 29, Randers C", null, "2014-08-29", true);
			createTravelJournal("Grækenland",
					"De er gale i hovedet og der er skrald over det hele! Rejser aldrig igen.",
					"Attiki Odos, Spata Artemida 190 04, Greece", null, "2008-12-06", false);
		}
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

	public TravelJournal createTravelJournal (String title, String note,
			String address, String picture, String dateVisited, boolean visitAgain)
	{
		TravelJournal newJournal = null;

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
		newJournal = createObjectFromCursor(newRow);
		newRow.close();
		return newJournal;
	}

	public List<TravelJournal> getAllTravelJournals()
	{
		List<TravelJournal> journals = new ArrayList<>();
		Cursor cursor = database.query(DeepThought.TABLE, DeepThought.ALL_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast())
		{
			TravelJournal newJournal = createObjectFromCursor(cursor);
			journals.add(newJournal);
			cursor.moveToNext();
		}
		return journals;
	}

	public List<TravelJournal> getAllTravelJournalsToVisitAgain()
	{
		List<TravelJournal> allJournals = getAllTravelJournals();
		List<TravelJournal> wantedJournals = new ArrayList<>();

		for (TravelJournal journal : allJournals)
			if (journal.isVisitAgain())
				wantedJournals.add(journal);

		return wantedJournals;
	}
}
