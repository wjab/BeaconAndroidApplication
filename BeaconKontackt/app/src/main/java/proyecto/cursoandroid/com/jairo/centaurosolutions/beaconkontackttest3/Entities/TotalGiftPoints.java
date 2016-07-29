package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Centauro on 16/06/2016.
 */
public class TotalGiftPoints implements Serializable {

    private String walkin;
    private String scan;
    private String purchase ;
    private String bill;

    public String getWalkin() {  return walkin; }
    public void setWalkin(String walkin) { this.walkin = walkin; }

    public String getBill() { return bill; }
    public void setBill(String bill) { this.bill = bill; }

    public String getScan() { return scan; }
    public void setScan(String scan) { this.scan = scan; }

    public String getPurchase() { return purchase; }
    public void setPurchase(String purchase) { this.purchase = purchase; }
}
