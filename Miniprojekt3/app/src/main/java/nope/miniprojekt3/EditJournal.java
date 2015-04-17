package nope.miniprojekt3;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


public class EditJournal extends ActionBarActivity
{
	private TravelJournalDAO dao;
	private TravelJournal journal;

	private EditText newTitle;
	private EditText newNote;
	private EditText newAddress;
	private EditText newPicture;
	private DatePicker newDate;
	private CheckBox newVisitAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_journal);
		Intent intent = getIntent();
		journal = (TravelJournal) intent.getSerializableExtra("journal");
		dao = TravelJournalDAO.getInstance(getApplicationContext());

		newTitle = (EditText) findViewById(R.id.newTitle);
		newNote = (EditText) findViewById(R.id.newNote);
		newAddress = (EditText) findViewById(R.id.newAddress);
		newPicture = (EditText) findViewById(R.id.newPicture);
		newDate = (DatePicker) findViewById(R.id.newDate);
		newVisitAgain = (CheckBox) findViewById(R.id.newVisitAgain);

		newTitle.setText(journal.getTitle());
		newNote.setText(journal.getNote());
		newAddress.setText(journal.getAddress());
		newPicture.setText(journal.getPicture());
		LocalDate date = journal.getDateVisited();
		newDate.init(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), null);
		newVisitAgain.setChecked(journal.isVisitAgain());
	}

	public void updateJournal(View view)
	{
		String title = newTitle.getText().toString();
		String note = newNote.getText().toString();
		String address = newAddress.getText().toString();
		String picture = newPicture.getText().toString();
		String date = new LocalDate(newDate.getYear(), newDate.getMonth(),
				newDate.getDayOfMonth()).toString();
		boolean visitAgain = newVisitAgain.isChecked();

		dao.updateTravelJournal(journal, title, note, address, picture, date, visitAgain);
		Toast tempToast = Toast.makeText(this, title + " was updated. Oh and please update " +
				"the list manually.", Toast.LENGTH_SHORT);
		tempToast.show();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_edit_journal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
