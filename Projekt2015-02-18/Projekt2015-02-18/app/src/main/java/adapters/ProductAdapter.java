package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import henrikbn.projekt2015_02_18.R;
import model.Product;

/**
 * Created by Henrik on 19-02-2015.
 */
public class ProductAdapter extends ArrayAdapter<Product>
{
	public ProductAdapter(Context context, List<Product> products)
	{
		super(context, 0, products);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// Get the data item for this position
		Product product = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null)
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_products, parent, false);

		// Lookup view for data population
		TextView productName = (TextView) convertView.findViewById(R.id.listView_productName);
		TextView productPrice = (TextView) convertView.findViewById(R.id.listView_productPrice);

		// Populate the data into the template view using the data object
		productName.setText(product.getName());
		productPrice.setText(product.getPrice() + "");

		// Return the completed view to render on screen
		return convertView;
	}
}
