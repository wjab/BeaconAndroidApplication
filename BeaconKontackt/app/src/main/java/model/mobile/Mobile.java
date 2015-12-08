package model.mobile;


import java.util.Date;

/**
 * Created by Eduardo on 24/11/2015.
 */
public class Mobile {

    private String Id;
    private String deviceName;
    private String deviceModel;
    private String osName;
    private String osVersion;
    private String userId;
    private Date registered;


    /**
     *
     */
    public Mobile() {
        // TODO Auto-generated constructor stub
    }


    /**
     * @param deviceName
     * @param deviceModel
     * @param osName
     * @param osVersion
     * @param userId
     * @param registered
     */
    public Mobile(String deviceName, String deviceModel, String osName, String osVersion, String userId,
                  Date registered) {
        super();
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.osName = osName;
        this.osVersion = osVersion;
        this.userId = userId;
        this.registered = registered;
    }


    /**
     * @return the id
     */
    public String getId() {
        return Id;
    }


    /**
     * @param id the id to set
     */
    public void setId(String id) {
        Id = id;
    }


    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }


    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    /**
     * @return the deviceModel
     */
    public String getDeviceModel() {
        return deviceModel;
    }


    /**
     * @param deviceModel the deviceModel to set
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }


    /**
     * @return the osName
     */
    public String getOsName() {
        return osName;
    }


    /**
     * @param osName the osName to set
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }


    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return osVersion;
    }


    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }


    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * @return the registered
     */
    public Date getRegistered() {
        return registered;
    }


    /**
     * @param registered the registered to set
     */
    public void setRegistered(Date registered) {
        this.registered = registered;
    }


}
