package henrikbn.projekt2015_02_18;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import model.DAO;
import model.Distributor;
import adapters.DistributorAdapter;


public class Distributors extends ActionBarActivity
{
	private DAO dao;
	private DistributorAdapter distributorsAdapter;
	private List<Distributor> distributors;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributors);

		dao = DAO.getInstance();
		distributors = dao.getDistributors();
		distributorsAdapter = new DistributorAdapter(this, distributors);

		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(distributorsAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				editDistributor(position);
			}
		});

		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void editDistributor(int position)
	{
		Intent intent = new Intent(this, Distributors_Products.class);
		Distributor distributor = (Distributor) listView.getItemAtPosition(position);
		intent.putExtra(ModelType.DISTRIBUTOR, distributor);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_distributors, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		Intent intent = null;

		switch (id)
		{
			case (R.id.distributors_add):
				intent = new Intent(this, Distributors_Add.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
