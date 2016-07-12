package model.user;

import java.util.Date;

/**
 * Created by Eduardo on 30/11/2015.
 */
public class User {

    private String id;

    private String user;

    private String password;

    private Boolean enable;



    private int categoryId;



    private int totalGiftPoints;

    private Date  creationDate;

    private Date modifiedDate;


    public User() {
        // TODO Auto-generated constructor stub
    }


    /**
     * @param user
     * @param password
     * @param enable
     * @param categoryId
     * @param totalGiftPoints
     * @param creationDate
     * @param modifiedDate
     */
    public User(String user, String password, Boolean enable, int categoryId, int totalGiftPoints, Date creationDate,
                Date modifiedDate) {
        super();
        this.user = user;
        this.password = password;
        this.enable = enable;
        this.categoryId = categoryId;
        this.totalGiftPoints = totalGiftPoints;
        this.creationDate = creationDate;
        this.modifiedDate = modifiedDate;
    }


    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }



    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the totalGiftPoints
     */
    public int getTotalGiftPoints() {
        return totalGiftPoints;
    }



    /**
     * @param totalGiftPoints the totalGiftPoints to set
     */
    public void setTotalGiftPoints(int totalGiftPoints) {
        this.totalGiftPoints = totalGiftPoints;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the modifiedDate
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param modifiedDate the modifiedDate to set
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }





}
