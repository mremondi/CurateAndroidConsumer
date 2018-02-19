package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mremondi on 2/19/18.
 */

public class CurateAPIUserPost {
    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("Username")
    @Expose
    private String Username;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("LoyaltyPoints")
    @Expose
    private Integer LoyaltyPoints;
    @SerializedName("FullName")
    @Expose
    private String FirstName;
    @SerializedName("FirstName")
    @Expose
    private String FullName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("DOB")
    @Expose
    private String DOB;
    @SerializedName("Age")
    @Expose
    private Integer Age;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Picture")
    @Expose
    private String Picture;
    @SerializedName("FacebookToken")
    @Expose
    private String FacebookToken;
    @SerializedName("GoogleToken")
    @Expose
    private String GoogleToken;
    @SerializedName("InstagramToken")
    @Expose
    private String InstagramToken;

    public CurateAPIUserPost(Integer ID, String Username, String Email,
                            Integer LoyaltyPoints, String FirstName, String FullName,
                            String LastName, String DOB, Integer Age, String Gender,
                            String Picture, String FacebookToken, String GoogleToken,
                            String InstagramToken) {
        this.ID = ID;
        this.Username = Username;
        this.Email = Email;
        this.LoyaltyPoints = LoyaltyPoints;
        this.FirstName = FirstName;
        this.FullName = FullName;
        this.LastName = LastName;
        this.DOB = DOB;
        this.Age = Age;
        this.Gender = Gender;
        this.Picture = Picture;
        this.FacebookToken = FacebookToken;
        this.GoogleToken = GoogleToken;
        this.InstagramToken = InstagramToken;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Integer getLoyaltyPoints() {
        return LoyaltyPoints;
    }

    public void setLoyaltyPoints(Integer LoyaltyPoints) {
        this.LoyaltyPoints = LoyaltyPoints;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer Age) {
        this.Age = Age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }

    public String getFacebookToken() {
        return FacebookToken;
    }

    public void setFacebookToken(String FacebookToken) {
        this.FacebookToken = FacebookToken;
    }

    public String getGoogleToken() {
        return GoogleToken;
    }

    public void setGoogleToken(String GoogleToken) {
        this.GoogleToken = GoogleToken;
    }

    public String getInstagramToken() {
        return InstagramToken;
    }

    public void setInstagramToken(String InstagramToken) {
        this.InstagramToken = InstagramToken;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
}
