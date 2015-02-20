package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HenrikBN on 18-02-2015.
 */
public class DAO
{
	private List<Distributor> distributors;
	private List<ShoppingList> shoppingLists;
	private Distributor activeDistributor;

	private static DAO instance;

	private DAO()
	{
		distributors = new ArrayList<>();
		shoppingLists = new ArrayList<>();
		populate();
	}

	public static DAO getInstance()
	{
		if (instance == null)
			instance = new DAO();
		return instance;
	}

	public void populate()
	{
		Distributor bob = new Distributor("Bob", "Address boulevard 1");
		Distributor yoloSwag = new Distributor("Derpenstein McYoloSwag", "Over there");
		distributors.add(bob);
		distributors.add(yoloSwag);

		Product milk = bob.addProduct(6.95, "Milk");
		Product bacon = bob.addProduct(5, "Bacon Deluxe");
		Product mercedes = bob.addProductOnSale(9001.01, "Mercedes Benz");

		Product milk2 = yoloSwag.addProduct(4.99, "Milk");
		Product bacon2 = yoloSwag.addProduct(4.2, "Bacon");
		Product shrubbery = yoloSwag.addProductOnSale(99.95, "Shrubbery");
		Product prunejuice = yoloSwag.addProductOnSale(0.99, "Prune juice - a warrior's drink");
		Product bonzibuddy = yoloSwag.addProductOnSale(0.0, "Bonzi Buddy!");

		ShoppingList list1 = new ShoppingList("Husk på vejen hjem");
		shoppingLists.add(list1);
		list1.addPurchase(2, mercedes);
		list1.addPurchase(1, milk2);

		ShoppingList list2 = new ShoppingList("Ønskeliste");
		shoppingLists.add(list2);
		list2.addPurchase(100, bacon);
		list2.addPurchase(50, bacon2);
		list2.addPurchase(5, prunejuice);
	}

	public Distributor getDistributor(String name)
	{
		for (Distributor distributor : distributors)
			if (name.equals(distributor.getName()))
				return distributor;
		return null;
	}

	public List<Distributor> getDistributors()
	{
		return distributors;
	}

	public Distributor getActiveDistributor()
	{
		return activeDistributor;
	}

	public void setActiveDistributor(Distributor activeDistributor)
	{
		this.activeDistributor = activeDistributor;
	}

	public List<ShoppingList> getShoppingLists()
	{
		return shoppingLists;
	}

	public void setShoppingLists(List<ShoppingList> shoppingLists)
	{
		this.shoppingLists = shoppingLists;
	}
}
