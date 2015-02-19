package model;

import java.io.Serializable;

/**
 * Created by HenrikBN on 18-02-2015.
 */
public class Product implements Comparable<Product>, Serializable
{
	private double price;
	private String name;

	public Product(double newPrice, String newName)
	{
		this.price = newPrice;
		this.name = newName;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	@Override
	public int compareTo(Product another)
	{
		return name.compareToIgnoreCase(another.getName());
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}
}
