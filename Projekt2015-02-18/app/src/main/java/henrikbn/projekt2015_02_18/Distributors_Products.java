package henrikbn.projekt2015_02_18;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import model.DAO;
import model.Distributor;


public class Distributors_Products extends ActionBarActivity
{
	private DAO dao = DAO.getInstance();
	private Distributor distributor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributors_products);

		Intent intent = getIntent();
		distributor = (Distributor) intent.getSerializableExtra(ModelType.DISTRIBUTOR);

		if (distributor != null)
			setTitle(distributor.getName());

		// savedInstanceState.putSerializable(ModelType.DISTRIBUTOR, distributor);
		// savedInstanceState.putString(ModelType.DISTRIBUTOR, distributor.getName());

		// TextView textView = (TextView) findViewById(R.id.textView);
		// textView.setText(distributor.getName());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// distributor = (Distributor) savedInstanceState.getSerializable(ModelType.DISTRIBUTOR);
		super.onRestoreInstanceState(savedInstanceState);
		String distributorName = savedInstanceState.getString(ModelType.DISTRIBUTOR);
		distributor = dao.getDistributor(distributorName);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_distributors_products, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		switch (id)
		{
			case (R.id.distributors_products_add):
				Intent intent = new Intent(this, Distributors_Products_Add.class);
				intent.putExtra(ModelType.DISTRIBUTOR, distributor);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
