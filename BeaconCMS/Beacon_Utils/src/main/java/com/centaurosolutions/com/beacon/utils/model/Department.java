package com.centaurosolutions.com.beacon.utils.model;

import java.util.ArrayList;

/**
 * @author Jairo
 *
 */
public class Department 
{
	private String name;
	private ArrayList<Product> products;
	
	public Department(String name, ArrayList<Product> products) 
	{
		this.name = name;
		this.products = products;
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public ArrayList<Product> getProducts() { return products; }
	public void setProducts(ArrayList<Product> products) { this.products = products; }

	
}
