package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;

/**
 * Created by Centauro on 04/08/2016.
 */
public class User implements Serializable{
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String gender;
    private String birthday;

    public User (){

    }
    public  User ( String name, String lastName, String phone, String email, String gender, String birthday){
        this.name= name;
        this.lastName =  lastName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }


    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender;  }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }


}
