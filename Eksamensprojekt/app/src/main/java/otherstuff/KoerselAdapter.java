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
//		public static final String KOERSEL_ID = "_id";
//		public static final String KOERSEL_ADRESSE_FRA = "adresse_fra";
//		public static final String KOERSEL_ADRESSE_TIL = "adresse_til";
//		public static final String KOERSEL_REG_NR = "reg_nr";
//		public static final String KOERSEL_DATO_FRA = "dato_fra";
//		public static final String KOERSEL_DATO_TIL = "dato_til";
//		public static final String KOERSEL_KM_FRA = "km_fra";
//		public static final String KOERSEL_KM_TIL = "km_til";
//		public static final String KOERSEL_KM_I_ALT = "km_i_alt";
//		public static final String KOERSEL_FORMAAL = "formaal";

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
		datoView.setText(datoFra + " → " + adresseTil);
		kmView.setText("" + kmFra + " → " + kmTil + " (" + kmIAlt + ")");
		formaalView.setText(formaal);
	}

	private String forkortStreng(String streng, int maksTegn)
	{
		if (streng.length() > maksTegn)
			return streng.substring(maksTegn);
		else
			return streng;
	}
}
