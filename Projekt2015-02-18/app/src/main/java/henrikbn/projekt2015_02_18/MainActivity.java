package henrikbn.projekt2015_02_18;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import model.DAO;
import model.Distributor;
import model.ShoppingList;


public class MainActivity extends ActionBarActivity
{
	private DAO dao = DAO.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Spinner distributorsView = (Spinner) findViewById(R.id.shopping_lists);
		ArrayAdapter<ShoppingList> distributorsAdapter =
				new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dao.getShoppingLists());
		distributorsView.setAdapter(distributorsAdapter);

		distributorsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// Opdater listview D;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		Intent intent = null;

		switch (id)
		{
			case (R.id.distributors):
				intent = new Intent(this, Distributors.class);
				startActivity(intent);
				return true;
			case (R.id.shopping_lists):
				intent = new Intent(this, Shopping_lists.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
