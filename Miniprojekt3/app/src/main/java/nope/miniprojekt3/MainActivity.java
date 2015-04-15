package nope.miniprojekt3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;


public class MainActivity extends ActionBarActivity
{
	private TravelJournalDAO dao;
	// private TemporaryFailDAO dao;

	private List<TravelJournal> journals;
	private ListView journalsView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dao = TravelJournalDAO.getInstance(this);
		// dao = TemporaryFailDAO.getInstance();

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				updateListView();
			}
		});
		updateListView();
	}

	private void updateListView()
	{
		RadioButton filterAll = (RadioButton) findViewById(R.id.filterAll);
		RadioButton filterVisitAgain = (RadioButton) findViewById(R.id.filterVisitAgain);

		if (filterVisitAgain.isChecked())
			journals = dao.getAllTravelJournalsToVisitAgain();
		else
			journals = dao.getAllTravelJournals();

		ArrayAdapter<TravelJournal> journalAdapter = new TravelJournalAdapter(this, journals);
		journalsView = (ListView) findViewById(R.id.journalsView);
		journalsView.setAdapter(journalAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.add_journal)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
