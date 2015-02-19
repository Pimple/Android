package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenrikBN on 18-02-2015.
 */
public class Distributor implements Comparable<Distributor>, Serializable
{
	private String name;
	private String address;
	private List<Product> products;
	private List<Product> productsOnSale;

	public Distributor(String gibberish)
	{
		String[] gibberishes = gibberish.split("::");
		this.name = gibberishes[0];
		this.address = gibberishes[1];
	}

	public Distributor(String newName, String newAddress)
	{
		this.name = newName;
		this.address = newAddress;
		this.products = new ArrayList<>();
		this.productsOnSale = new ArrayList<>();
	}

	@Override
	public String toString()
	{
		return this.name + "::" + this.address;
	}

	@Override
	public int compareTo(Distributor another)
	{
		return this.name.compareToIgnoreCase(another.getName());
	}

	public Product addProduct(double price, String name)
	{
		Product newProduct = new Product(price, name);
		this.products.add(newProduct);
		return newProduct;
	}
	public void removeProduct(Product product)
	{
		this.products.remove(product);
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public Product addProductOnSale(double price, String name)
	{
		Product newProduct = new Product(price, name);
		this.productsOnSale.add(newProduct);
		return newProduct;
	}
	public void removeProductOnSale(Product product)
	{
		this.productsOnSale.remove(product);
	}

	public List<Product> getProductsOnSale()
	{
		return productsOnSale;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}
}
