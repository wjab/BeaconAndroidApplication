package app.model;
import org.springframework.data.annotation.Id;


public class SavedProducts {

	private int merchant_id;
	private int shopZone_id;
	private int product_id;
	
	
	public int getMerchant_id() {
		return merchant_id;
	}


	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}


	public int getShopZone_id() {
		return shopZone_id;
	}


	public void setShopZone_id(int shopZone_id) {
		this.shopZone_id = shopZone_id;
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public SavedProducts() {
		// TODO Auto-generated constructor stub
	}
	
	public SavedProducts(int pMerchantId, int pShopZoneId, int pProduct_id){

		this.product_id = pProduct_id;
		this.shopZone_id = pShopZoneId;
		this.merchant_id = pMerchantId;
	}

}
