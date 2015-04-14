package nope.miniprojekt3;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenrikBN on 14-04-2015.
 */
public class TemporaryFailDAO
{
	private static TemporaryFailDAO instance;
	private List<TravelJournal> journals;

	private TemporaryFailDAO ()
	{
		journals = new ArrayList<>();
		populate();
	}

	public static TemporaryFailDAO getInstance ()
	{
		if (instance == null)
			synchronized (TemporaryFailDAO.class)
			{
				if (instance == null)
					instance = new TemporaryFailDAO();
			}
		return instance;
	}

	private void populate()
	{
		if (journals.isEmpty())
		{
			createTravelJournal("Bingo", "Det var godt nok sjov`!", "Østervold 1, 8900 Randers," +
							" Danmark", null, "2015-03-15", true);
			createTravelJournal("Læst en bog \"i det fri\"", "Endnu en dag. Endnu et eventyr. Og " +
					"bogen var ikke helt dårlig. Kun næsten.","Jernbanegade 29, Randers C", null,
					"2014-08-29", true);
			createTravelJournal("Grækenland",
					"De er gale i hovedet og der er skrald over det hele! Rejser aldrig igen.",
					"Attiki Odos, Spata Artemida 190 04, Greece", null, "2008-12-06", false);
		}
	}

	public TravelJournal createTravelJournal(String title, String note, String address, String picture, String dateVisited, boolean visitAgain)
	{
		int id = journals.size();
		TravelJournal newJournal = new TravelJournal(id, title, note, address, picture, dateVisited, visitAgain);
		journals.add(newJournal);
		return newJournal;
	}
}
