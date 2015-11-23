package model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Eduardo on 13/11/2015.
 */
public class BeaconCache implements Serializable {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String proximity;
    @DatabaseField
    public String uniqueID;
    @DatabaseField
    public String message;
    @DatabaseField
    public Double expiration;
    @DatabaseField
    public String promoId;


    public BeaconCache(){

    }

}
