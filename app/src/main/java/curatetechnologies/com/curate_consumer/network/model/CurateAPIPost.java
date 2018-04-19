package curatetechnologies.com.curate_consumer.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mremondi on 2/22/18.
 */

public class CurateAPIPost {

    @SerializedName("Post_ID")
    @Expose
    private Integer postID;
    @SerializedName("Post_PostType")
    @Expose
    private String postPostType;
    @SerializedName("Restaurant_ID")
    @Expose
    private Integer restaurantID;
    @SerializedName("Item_ID")
    @Expose
    private Integer itemID;
    @SerializedName("Post_Description")
    @Expose
    private String postDescription;
    @SerializedName("Post_Rating")
    @Expose
    private String postRating;
    @SerializedName("Post_NumberOfLikes")
    @Expose
    private Integer postNumberOfLikes;
    @SerializedName("Post_NumberOfSaves")
    @Expose
    private Integer postNumberOfSaves;
    @SerializedName("Post_ImageURL")
    @Expose
    private String postImageURL;
    @SerializedName("Post_Time")
    @Expose
    private String postTime;
    @SerializedName("User_ID")
    @Expose
    private Integer userID;
    @SerializedName("User_Username")
    @Expose
    private String userUsername;
    @SerializedName("User_Picture")
    @Expose
    private String userPicture;
    @SerializedName("Item_Name")
    @Expose
    private String itemName;
    @SerializedName("Restaurant_Name")
    @Expose
    private String restaurantName;
    @SerializedName("distance_in_miles")
    @Expose
    private Double distanceInMiles;

    public CurateAPIPost(Integer postID, String postPostType, Integer restaurantID,
                         Integer itemID, String postDescription, String postRating,
                         Integer postNumberOfLikes, Integer postNumberOfSaves, String postImageURL,
                         String postTime, Integer userID, String userUsername, String userPicture,
                         String itemName, String restaurantName, Double distanceInMiles) {
        this.postID = postID;
        this.postPostType = postPostType;
        this.restaurantID = restaurantID;
        this.itemID = itemID;
        this.postDescription = postDescription;
        this.postRating = postRating;
        this.postNumberOfLikes = postNumberOfLikes;
        this.postNumberOfSaves = postNumberOfSaves;
        this.postImageURL = postImageURL;
        this.postTime = postTime;
        this.userID = userID;
        this.userUsername = userUsername;
        this.userPicture = userPicture;
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.distanceInMiles = distanceInMiles;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public String getPostPostType() {
        return postPostType;
    }

    public void setPostPostType(String postPostType) {
        this.postPostType = postPostType;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostRating() {
        return postRating;
    }

    public void setPostRating(String postRating) {
        this.postRating = postRating;
    }

    public Object getPostNumberOfLikes() {
        return postNumberOfLikes;
    }

    public void setPostNumberOfLikes(Integer postNumberOfLikes) {
        this.postNumberOfLikes = postNumberOfLikes;
    }

    public Integer getPostNumberOfSaves() {
        return postNumberOfSaves;
    }

    public void setPostNumberOfSaves(Integer postNumberOfSaves) {
        this.postNumberOfSaves = postNumberOfSaves;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
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
