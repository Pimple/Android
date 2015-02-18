package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenrikBN on 18-02-2015.
 */
public class ShoppingList implements Comparable<ShoppingList>
{
	private String name;
	private List<Purchase> purchases;

	public ShoppingList(String newName)
	{
		this.name = name;
		this.purchases = new ArrayList<>();
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	@Override
	public int compareTo(ShoppingList another)
	{
		return this.name.compareToIgnoreCase(another.getName());
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
