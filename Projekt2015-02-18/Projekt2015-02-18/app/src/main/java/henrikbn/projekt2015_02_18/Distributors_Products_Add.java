package henrikbn.projekt2015_02_18;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import model.DAO;
import model.Distributor;


public class Distributors_Products_Add extends ActionBarActivity
{
	private DAO dao = DAO.getInstance();
	private Distributor distributor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributors_products__add);

		Intent intent = getIntent();
		// distributor = (Distributor) intent.getSerializableExtra("distributor");
		distributor = dao.getActiveDistributor();

		SharedPreferences preferences = getPreferences(0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(ModelType.DISTRIBUTOR, distributor.getName());
		editor.apply();
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

	public void addProduct(View view)
	{
		Intent intent = new Intent(this, Distributors_Products_Add.class);
		EditText nameField = (EditText) findViewById(R.id.newProductName);
		EditText priceField = (EditText) findViewById(R.id.newProductPrice);
		String name = nameField.getText().toString();
		double price = Double.parseDouble(priceField.getText().toString());
		distributor.addProduct(price, name);
		finish();
	}
}
