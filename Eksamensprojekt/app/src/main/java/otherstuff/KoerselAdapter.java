package otherstuff;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henrikbn.eksamensprojekt.R;

import org.joda.time.LocalDateTime;

import data.DAO;
// import android.widget.CursorAdapter;

/**
 * Created by Henrik on 21-05-2015.
 */
public class KoerselAdapter extends android.support.v4.widget.CursorAdapter
{

	public KoerselAdapter(Context context, Cursor cursor)
	{
		super(context, cursor, 0);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.koersel_view, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		DAO dao = DAO.getInstance(view.getContext());

		int adresseFraId = cursor.getInt(cursor.getColumnIndex("adresse_fra"));
		int adresseTilId = cursor.getInt(cursor.getColumnIndex("adresse_til"));
		String adresseFra = dao.hentAdresse(adresseFraId);
		String adresseTil = dao.hentAdresse(adresseTilId);
		String regNr = cursor.getString(cursor.getColumnIndex("reg_nr"));
		String datoFra = cursor.getString(cursor.getColumnIndex("dato_fra"));
		String datoTil = cursor.getString(cursor.getColumnIndex("dato_til"));
		double kmFra = cursor.getDouble(cursor.getColumnIndex("km_fra"));
		double kmTil = cursor.getDouble(cursor.getColumnIndex("km_til"));
		int kmIAlt = cursor.getInt(cursor.getColumnIndex("km_i_alt"));
		String formaal = cursor.getString(cursor.getColumnIndex("formaal"));

		if (formaal.length() > 30)
			formaal = formaal.substring(30) + " ...";

		TextView adresseView = (TextView) view.findViewById(R.id.adresse);
		TextView regNrView = (TextView) view.findViewById(R.id.reg_nr);
		TextView datoView = (TextView) view.findViewById(R.id.dato);
		TextView kmView = (TextView) view.findViewById(R.id.km);
		TextView formaalView = (TextView) view.findViewById(R.id.formaal);

		adresseView.setText(forkortStreng(adresseFra, 10) + " → " + forkortStreng(adresseTil, 10));
		regNrView.setText(regNr);
		datoView.setText(forkortStreng(datoFra, 10) + " → " + forkortStreng(datoTil, 10));
		kmView.setText("" + kmFra + "km → " + kmTil + "km (" + kmIAlt + "km)");
		formaalView.setText(formaal);
	}

	private String forkortStreng(String streng, int maksTegn)
	{
		if (streng.length() > maksTegn)
			return streng.substring(0, maksTegn);
		else
			return streng;
	}
}
