package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mremondi on 4/15/18.
 */

public class CurateAPILoginUser {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    public CurateAPILoginUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public class User {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Username")
        @Expose
        private String username;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("LoyaltyPoints")
        @Expose
        private Object loyaltyPoints;
        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("Age")
        @Expose
        private Integer age;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Friends")
        @Expose
        private Object friends;
        @SerializedName("Picture")
        @Expose
        private String picture;
        @SerializedName("StripeId")
        @Expose
        private String stripeId;
        @SerializedName("FacebookToken")
        @Expose
        private String facebookToken;
        @SerializedName("GoogleToken")
        @Expose
        private String googleToken;
        @SerializedName("InstagramToken")
        @Expose
        private String instagramToken;

        public User(Integer iD, String username, String email, String password,
                    Object loyaltyPoints, String fullName, String firstName,
                    String lastName, String dOB, Integer age, String gender, Object friends,
                    String picture, String stripeId, String facebookToken, String googleToken,
                    String instagramToken) {
            this.iD = iD;
            this.username = username;
            this.email = email;
            this.password = password;
            this.loyaltyPoints = loyaltyPoints;
            this.fullName = fullName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dOB = dOB;
            this.age = age;
            this.gender = gender;
            this.friends = friends;
            this.picture = picture;
            this.stripeId = stripeId;
            this.facebookToken = facebookToken;
            this.googleToken = googleToken;
            this.instagramToken = instagramToken;
        }

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getLoyaltyPoints() {
            return loyaltyPoints;
        }

        public void setLoyaltyPoints(Object loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDOB() {
            return dOB;
        }

        public void setDOB(String dOB) {
            this.dOB = dOB;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getFriends() {
            return friends;
        }

        public void setFriends(Object friends) {
            this.friends = friends;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getStripeId() {
            return stripeId;
        }

        public void setStripeId(String stripeId) {
            this.stripeId = stripeId;
        }

        public String getFacebookToken() {
            return facebookToken;
        }

        public void setFacebookToken(String facebookToken) {
            this.facebookToken = facebookToken;
        }

        public String getGoogleToken() {
            return googleToken;
        }

        public void setGoogleToken(String googleToken) {
            this.googleToken = googleToken;
        }

        public String getInstagramToken() {
            return instagramToken;
        }

        public void setInstagramToken(String instagramToken) {
            this.instagramToken = instagramToken;
        }
    }
}
