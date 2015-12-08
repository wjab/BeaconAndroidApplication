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

    private int category_id;

    private int total_gift_points;

    private Date  creationDate;

    private Date modifiedDate;


    public User() {
        // TODO Auto-generated constructor stub
    }


    /**
     * @param user
     * @param password
     * @param enable
     * @param category_id
     * @param total_gift_points
     * @param creationDate
     * @param modifiedDate
     */
    public User(String user, String password, Boolean enable, int category_id, int total_gift_points, Date creationDate,
                Date modifiedDate) {
        super();
        this.user = user;
        this.password = password;
        this.enable = enable;
        this.category_id = category_id;
        this.total_gift_points = total_gift_points;
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
     * @return the category_id
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id the category_id to set
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    /**
     * @return the total_gift_points
     */
    public int getTotal_gift_points() {
        return total_gift_points;
    }

    /**
     * @param total_gift_points the total_gift_points to set
     */
    public void setTotal_gift_points(int total_gift_points) {
        this.total_gift_points = total_gift_points;
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
