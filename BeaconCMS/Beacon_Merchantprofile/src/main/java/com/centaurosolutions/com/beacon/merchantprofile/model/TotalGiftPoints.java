package com.centaurosolutions.com.beacon.merchantprofile.model;

import java.util.ArrayList;
/**
 * @author Jairo
 *
 */
public class TotalGiftPoints 
{
	private String walkin;
	private String scan;
	private String purchase;
	private String bill;
	/**
	 * @param walkin
	 * @param scan
	 * @param purchase
	 * @param bill
	 * 
	 */
	public TotalGiftPoints(String walkin, String scan, String purchase, String bill) {
		this.walkin = walkin;
		this.scan = scan;
		this.purchase= purchase;
		this.bill = bill;
	}
	public TotalGiftPoints() {
	}
	
	public String getWalkin() { return walkin; }	
	public void setWalkin(String walkin) { this.walkin = walkin; }
	
	public String getScan() { return scan; }
	public void setScan(String scan) { this.scan = scan; }
	
	public String getPurchase() { return purchase; }
	public void setPurchase(String purchase) { this.purchase = purchase; }
	
	public String getBill() { return bill; }
	public void setBill(String bill) { this.bill = bill; }
	
}
