package curatetechnologies.com.curate_consumer.network.model;

/**
 * Created by mremondi on 2/9/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurateAPIItem {

    @SerializedName("Item_PreferenceMatch")
    @Expose
    private Boolean itemPreferenceMatch;

    @SerializedName("Item_NumberOfOverallRatings")
    @Expose
    private Integer itemNumberOfOverallRatings;

    @SerializedName("Restaurant_ID")
    @Expose
    private Integer restaurantID;

    @SerializedName("Restaurant_StripeID")
    @Expose
    private String restaurantStripeID;

    @SerializedName("distance_in_miles")
    @Expose
    private Double distanceInMiles;

    @SerializedName("MenuSection_Name")
    @Expose
    private String menuSectionName;

    @SerializedName("Item_SumOfOverallRatings")
    @Expose
    private Integer itemSumOfOverallRatings;

    @SerializedName("Menu_Name")
    @Expose
    private String menuName;

    @SerializedName("Restaurant_Name")
    @Expose
    private String restaurantName;

    @SerializedName("Item_ImageURL")
    @Expose
    private String itemImageURL;

    @SerializedName("Item_ItemTags")
    @Expose
    private List<CurateAPIItemTag> itemItemTags = null;

    @SerializedName("Item_Name")
    @Expose
    private String itemName;

    @SerializedName("Item_ID")
    @Expose
    private Integer itemID;

    @SerializedName("MenuSection_ID")
    @Expose
    private Integer menuSectionID;

    @SerializedName("Item_Price")
    @Expose
    private Double itemPrice;

    @SerializedName("Item_Available")
    @Expose
    private Boolean itemAvailable;

    @SerializedName("Menu_ID")
    @Expose
    private Integer menuID;

    @SerializedName("Item_Description")
    @Expose
    private String itemDescription;

    public Boolean getItemPreferenceMatch() {
        return itemPreferenceMatch;
    }

    public void setItemPreferenceMatch(Boolean itemPreferenceMatch) {
        this.itemPreferenceMatch = itemPreferenceMatch;
    }

    public Integer getItemNumberOfOverallRatings() {
        return itemNumberOfOverallRatings;
    }

    public void setItemNumberOfOverallRatings(Integer itemNumberOfOverallRatings) {
        this.itemNumberOfOverallRatings = itemNumberOfOverallRatings;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Double getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(Double distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public String getMenuSectionName() {
        return menuSectionName;
    }

    public void setMenuSectionName(String menuSectionName) {
        this.menuSectionName = menuSectionName;
    }

    public Integer getItemSumOfOverallRatings() {
        return itemSumOfOverallRatings;
    }

    public void setItemSumOfOverallRatings(Integer itemSumOfOverallRatings) {
        this.itemSumOfOverallRatings = itemSumOfOverallRatings;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getItemImageURL() {
        return itemImageURL;
    }

    public void setItemImageURL(String itemImageURL) {
        this.itemImageURL = itemImageURL;
    }

    public List<CurateAPIItemTag> getItemItemTags() {
        return itemItemTags;
    }

    public void setItemItemTags(List<CurateAPIItemTag> itemItemTags) {
        this.itemItemTags = itemItemTags;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getMenuSectionID() {
        return menuSectionID;
    }

    public void setMenuSectionID(Integer menuSectionID) {
        this.menuSectionID = menuSectionID;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getItemAvailable() {
        return itemAvailable;
    }

    public void setItemAvailable(Boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getRestaurantStripeID() {
        return restaurantStripeID;
    }

    public void setRestaurantStripeID(String restaurantStripeID) {
        this.restaurantStripeID = restaurantStripeID;
    }
}
