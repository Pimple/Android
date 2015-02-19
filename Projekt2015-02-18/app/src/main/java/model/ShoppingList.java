package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenrikBN on 18-02-2015.
 */
public class ShoppingList implements Comparable<ShoppingList>, Serializable
{
	private String name;
	private List<Purchase> purchases;

	public ShoppingList(String newName)
	{
		this.name = newName;
		purchases = new ArrayList<>();
	}

	public Purchase addPurchase(int quantity, Product product)
	{
		Purchase newPurchase = new Purchase(quantity, product);
		purchases.add(newPurchase);
		return newPurchase;
	}
	public void removePurchase(Purchase purchase)
	{
		purchases.remove(purchase);
	}

	public List<Purchase> getPurchases()
	{
		return purchases;
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
