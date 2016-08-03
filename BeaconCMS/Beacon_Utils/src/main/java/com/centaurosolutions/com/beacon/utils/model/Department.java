package com.centaurosolutions.com.beacon.utils.model;

import java.util.ArrayList;

/**
 * @author Jairo
 *
 */
public class Department 
{
	String id;
	private String name;
	private ArrayList<Product> products;
	private String departmentUrl;
	
	public Department(String name, ArrayList<Product> products, String departmentUrl) 
	{
		this.name = name;
		this.products = products;
		this.departmentUrl = departmentUrl;
	}

	public Department(){

	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public ArrayList<Product> getProducts() { return products; }
	public void setProducts(ArrayList<Product> products) { this.products = products; }

	public String getId() {	return id;	}
	public void setId(String id) { this.id = id; }
	
	public String getDepartmentUrl() { return departmentUrl; }
	public void setDepartmentUrl(String departmentUrl) { this.departmentUrl = departmentUrl; }

}
