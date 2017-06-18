package models;

/**
 * Created by root on 5/17/17.
 */

public class user {
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String fullname;

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int age;

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int phonenumber;

    public int getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    private int type_id;

    public int getTypeId() {
        return this.type_id;
    }

    public void setTypeId(int type_id) {
        this.type_id = type_id;
    }

    private boolean gender;

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    private int country_id;

    public int getCountryId() {
        return this.country_id;
    }

    public void setCountryId(int country_id) {
        this.country_id = country_id;
    }

    private String photo_path;

    public String getPhotoPath() {
        return this.photo_path;
    }

    public void setPhotoPath(String photo_path) {
        this.photo_path = photo_path;
    }

    private country country;

    public country getCountry() {
        return this.country;
    }

    public void setCountry(country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return username;
    }
}
