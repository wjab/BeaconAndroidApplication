package model.device;

/**
 * Created by Eduardo on 30/11/2015.
 */
/**
 * @author Eduardo
 *
 */
public class Range {


    private String type;
    private String message;
    private String messageType;
    private String promoID;


    /**
     *
     */
    public Range() {
        // TODO Auto-generated constructor stub
    }

    public Range(String type, String message, String messageType, String promoID) {
        super();
        this.type = type;
        this.message = message;
        this.messageType = messageType;
        this.promoID = promoID;
    }

    /**
     * @return the promoID
     */
    public String getPromoID() {
        return promoID;
    }

    /**
     * @param promoID the promoID to set
     */
    public void setPromoID(String promoID) {
        this.promoID = promoID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
