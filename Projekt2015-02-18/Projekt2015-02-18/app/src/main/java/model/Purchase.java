package model;

import java.io.Serializable;

/**
 * Created by HenrikBN on 18-02-2015.
 */
public class Purchase implements Comparable<Purchase>, Serializable
{
	private int quantity;
	private Product product;

	public Purchase(int newQuantity, Product product)
	{
		this.quantity = newQuantity;
		this.product = product;
	}

	@Override
	public int compareTo(Purchase another)
	{
		return this.product.compareTo(another.getProduct());
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}
}
