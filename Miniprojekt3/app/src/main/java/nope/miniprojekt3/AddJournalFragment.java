package nope.miniprojekt3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class AddJournalFragment extends DialogFragment
{
	private TravelJournalDAO dao;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_add_journal, null);

		dao = TravelJournalDAO.getInstance(getActivity());

		final EditText newTitle = (EditText) view.findViewById(R.id.newTitle);
		final EditText newNote = (EditText) view.findViewById(R.id.newNote);
		final EditText newAddress = (EditText) view.findViewById(R.id.newAddress);
		final EditText newPicture = (EditText) view.findViewById(R.id.newPicture);
		final EditText newDate = (EditText) view.findViewById(R.id.newDate);
		final CheckBox newVisitAgain = (CheckBox) view.findViewById(R.id.newVisitAgain);

		builder.setView(view)
				.setPositiveButton(R.string.add_journal_save, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						String title = newTitle.getText().toString();
						String note = newNote.getText().toString();
						String address = newAddress.getText().toString();
						String picture = newPicture.getText().toString();
						String date = newDate.getText().toString();
						boolean visitAgain = newVisitAgain.isChecked();

						dao.createTravelJournal(title, note, address, picture, date, visitAgain);
					}
				})
				.setNegativeButton(R.string.add_journal_cancel, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id) {
						AddJournalFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
	}
}
