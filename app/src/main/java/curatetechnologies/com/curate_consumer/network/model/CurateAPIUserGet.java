package curatetechnologies.com.curate_consumer.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mremondi on 2/12/18.
 */

public class CurateAPIUserGet {
    @SerializedName("ID")
    @Expose
    private Integer userID;
    @SerializedName("Username")
    @Expose
    private String userUsername;
    @SerializedName("Email")
    @Expose
    private String userEmail;
    @SerializedName("LoyaltyPoints")
    @Expose
    private Integer userLoyaltyPoints;
    @SerializedName("FullName")
    @Expose
    private String userFirstName;
    @SerializedName("FirstName")
    @Expose
    private String userFullName;
    @SerializedName("LastName")
    @Expose
    private String userLastName;
    @SerializedName("DOB")
    @Expose
    private String userDOB;
    @SerializedName("Age")
    @Expose
    private Integer userAge;
    @SerializedName("Gender")
    @Expose
    private String userGender;
    @SerializedName("Picture")
    @Expose
    private String userPicture;
    @SerializedName("FacebookToken")
    @Expose
    private String userFacebookToken;
    @SerializedName("GoogleToken")
    @Expose
    private String userGoogleToken;
    @SerializedName("InstagramToken")
    @Expose
    private String userInstagramToken;
    @SerializedName("StripeId")
    @Expose
    private String userStripeId;


    public CurateAPIUserGet(Integer userID, String userUsername, String userEmail,
                            Integer userLoyaltyPoints, String userFirstName, String userFullName,
                            String userLastName, String userDOB, Integer userAge, String userGender,
                            String userPicture, String userFacebookToken, String userGoogleToken,
                            String userInstagramToken, String userStripeId) {
        this.userID = userID;
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userLoyaltyPoints = userLoyaltyPoints;
        this.userFirstName = userFirstName;
        this.userFullName = userFullName;
        this.userLastName = userLastName;
        this.userDOB = userDOB;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userPicture = userPicture;
        this.userFacebookToken = userFacebookToken;
        this.userGoogleToken = userGoogleToken;
        this.userInstagramToken = userInstagramToken;
        this.userStripeId = userStripeId;
    }

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

    public String getUserStripeId() {
        return userStripeId;
    }

    public void setUserStripeId(String userStripeId) {
        this.userStripeId = userStripeId;
    }
}
