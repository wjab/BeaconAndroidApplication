package com.centaurosolutions.com.beacon.merchantprofile.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Jairo
 *
 */
public class Department 
{

	private String id;
	private String name;
	private ArrayList<Product> products;

	public Department()
	{
		this.id = UUID.randomUUID().toString();
	}

	public Department(String name, ArrayList<Product> products) 
	{
		this.name = name;
		setProducts(products);
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public ArrayList<Product> getProducts() { return products; }
	public void setProducts(ArrayList<Product> products) { this.products = products; }


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
