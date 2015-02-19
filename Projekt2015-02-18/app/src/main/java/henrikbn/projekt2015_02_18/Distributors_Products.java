package henrikbn.projekt2015_02_18;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import adapters.ProductAdapter;
import model.DAO;
import model.Distributor;
import model.Product;


public class Distributors_Products extends ActionBarActivity
{
	private DAO dao = DAO.getInstance();
	private ProductAdapter productAdapter, productOnSaleAdapter;
	private Distributor distributor;
	private ListView productsView, productsOnSaleView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributors_products);

		Intent intent = getIntent();
		distributor = (Distributor) intent.getSerializableExtra(ModelType.DISTRIBUTOR);

		if (distributor == null)
		{
			SharedPreferences preferences = getPreferences(0);
			String distributorName = preferences.getString(ModelType.DISTRIBUTOR, ModelType.DEFAULT);
			distributor = dao.getDistributor(distributorName);
		}

		setTitle(distributor.getName());
		updateInterface();
		// savedInstanceState.putSerializable(ModelType.DISTRIBUTOR, distributor);
		// savedInstanceState.putString(ModelType.DISTRIBUTOR, distributor.getName());

		// TextView textView = (TextView) findViewById(R.id.textView);
		// textView.setText(distributor.getName());
	}

	protected void updateInterface()
	{
		List<Product> products = distributor.getProducts();
		List<Product> productsOnSale = distributor.getProductsOnSale();

		productAdapter = new ProductAdapter(this, products);
		productOnSaleAdapter = new ProductAdapter(this, productsOnSale);

		productsView = (ListView) findViewById(R.id.distributors_products_view);
		productsView.setAdapter(productAdapter);

		productsOnSaleView = (ListView) findViewById(R.id.distributors_productsOnSale_view);
		productsOnSaleView.setAdapter(productOnSaleAdapter);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// distributor = (Distributor) savedInstanceState.getSerializable(ModelType.DISTRIBUTOR);

		String distributorName = savedInstanceState.getString(ModelType.DISTRIBUTOR);
		distributor = dao.getDistributor(distributorName);

		updateInterface();

		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		distributor = (Distributor) intent.getSerializableExtra(ModelType.DISTRIBUTOR);
		updateInterface();
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
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
