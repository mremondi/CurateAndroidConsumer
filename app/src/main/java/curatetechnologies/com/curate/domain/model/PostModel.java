package curatetechnologies.com.curate.domain.model;

/**
 * Created by mremondi on 2/22/18.
 */

public class PostModel {

    private Integer id;
    private String postType;
    private Integer restaurantId;
    private Integer itemId;
    private String description;
    private Boolean rating;
    private Integer numberOfLikes;
    private Integer numberOfSaves;
    private String imageURL;
    private String time;
    private Integer userId;
    private String username;
    private String userPicture;
    private String itemName;
    private String restaurantName;
    private Double distanceInMiles;

    public PostModel(Integer id, String postType, Integer restaurantId, Integer itemId,
                     String description, Boolean rating, Integer numberOfLikes,
                     Integer numberOfSaves, String imageURL, String time, Integer userId,
                     String username, String userPicture, String itemName, String restaurantName,
                     Double distanceInMiles) {
        this.id = id;
        this.postType = postType;
        this.restaurantId = restaurantId;
        this.itemId = itemId;
        this.description = description;
        this.rating = rating;
        this.numberOfLikes = numberOfLikes;
        this.numberOfSaves = numberOfSaves;
        this.imageURL = imageURL;
        this.time = time;
        this.userId = userId;
        this.username = username;
        this.userPicture = userPicture;
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.distanceInMiles = distanceInMiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Integer getNumberOfSaves() {
        return numberOfSaves;
    }

    public void setNumberOfSaves(Integer numberOfSaves) {
        this.numberOfSaves = numberOfSaves;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Double getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(Double distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }
}
