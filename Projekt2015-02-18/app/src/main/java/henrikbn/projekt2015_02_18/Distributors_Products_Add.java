package henrikbn.projekt2015_02_18;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import model.Distributor;


public class Distributors_Products_Add extends ActionBarActivity
{
	private Distributor distributor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributors_products__add);

		Intent intent = getIntent();
		distributor = (Distributor) intent.getSerializableExtra("distributor");

		if (distributor != null)
			setTitle(distributor.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_distributors_products_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
