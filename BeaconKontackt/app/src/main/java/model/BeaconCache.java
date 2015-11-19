package model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Eduardo on 13/11/2015.
 */
public class BeaconCache {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    private String proximity;
    @DatabaseField
    private String uniqueID;
    @DatabaseField
    private String message;
    @DatabaseField
    private String expiration;
    @DatabaseField
    private String promoId;

    public BeaconCache(String proximityUUID, String uniqueID, String message, String expiration, String promoId) {
        this.proximity = proximityUUID;
        this.uniqueID = uniqueID;
        this.message = message;
        this.expiration = expiration;
        this.promoId = promoId;
    }

    public BeaconCache(){

    }

    public String getProximity() {
        return proximity;
    }

    public void setProximity(String proximity) {
        this.proximity = proximity;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }


    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }
}
