package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mremondi on 2/12/18.
 */

public class CurateAPIUser {
    @SerializedName("User_ID")
    @Expose
    private Integer userID;
    @SerializedName("User_Username")
    @Expose
    private String userUsername;
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("User_LoyaltyPoints")
    @Expose
    private Integer userLoyaltyPoints;
    @SerializedName("User_FullName")
    @Expose
    private String userFirstName;
    @SerializedName("User_FirstName")
    @Expose
    private String userFullName;
    @SerializedName("User_LastName")
    @Expose
    private String userLastName;
    @SerializedName("User_DOB")
    @Expose
    private String userDOB;
    @SerializedName("User_Age")
    @Expose
    private Integer userAge;
    @SerializedName("User_Gender")
    @Expose
    private String userGender;
    @SerializedName("User_Picture")
    @Expose
    private String userPicture;
    @SerializedName("User_FacebookToken")
    @Expose
    private String userFacebookToken;
    @SerializedName("User_GoogleToken")
    @Expose
    private String userGoogleToken;
    @SerializedName("User_InstagramToken")
    @Expose
    private String userInstagramToken;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserLoyaltyPoints() {
        return userLoyaltyPoints;
    }

    public void setUserLoyaltyPoints(Integer userLoyaltyPoints) {
        this.userLoyaltyPoints = userLoyaltyPoints;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserFacebookToken() {
        return userFacebookToken;
    }

    public void setUserFacebookToken(String userFacebookToken) {
        this.userFacebookToken = userFacebookToken;
    }

    public String getUserGoogleToken() {
        return userGoogleToken;
    }

    public void setUserGoogleToken(String userGoogleToken) {
        this.userGoogleToken = userGoogleToken;
    }

    public String getUserInstagramToken() {
        return userInstagramToken;
    }

    public void setUserInstagramToken(String userInstagramToken) {
        this.userInstagramToken = userInstagramToken;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }
}
