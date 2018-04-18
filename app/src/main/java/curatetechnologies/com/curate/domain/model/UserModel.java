package curatetechnologies.com.curate.domain.model;


public class UserModel {
    private Integer id;
    private String username;
    private String email;
    private int loyaltyPoints;
    private String fullName;
    private String firstName;
    private String lastName;
    private String birthday;
    private int age;
    private String gender;
    private String profilePictureURL;
    private String facebookToken;
    private String googleToken;
    private String curateToken;
    private String stripeId;

    public UserModel(){}

    public UserModel(Integer id, String username, String email, int loyaltyPoints, String fullName,
                     String firstName, String lastName, String birthday, int age, String gender,
                     String profilePictureURL, String facebookToken, String googleToken,
                     String curateToken, String stripeId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.loyaltyPoints = loyaltyPoints;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.age = age;
        this.gender = gender;
        this.profilePictureURL = profilePictureURL;
        this.facebookToken = facebookToken;
        this.googleToken = googleToken;
        this.curateToken = curateToken;
        this.stripeId = stripeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getFullName() {
        if (fullName.equals("")){
            return firstName + " " + lastName;
        }
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
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

    public String getCurateToken() {
        return curateToken;
    }

    public void setCurateToken(String curateToken) {
        this.curateToken = curateToken;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }
}
