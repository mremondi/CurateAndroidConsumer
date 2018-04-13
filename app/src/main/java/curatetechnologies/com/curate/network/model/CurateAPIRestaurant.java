package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurateAPIRestaurant {

    @SerializedName("Restaurant_ID")
    @Expose
    private Integer restaurantID;
    @SerializedName("Restaurant_Name")
    @Expose
    private String restaurantName;
    @SerializedName("Restaurant_Description")
    @Expose
    private String restaurantDescription;
    @SerializedName("Restaurant_URL")
    @Expose
    private String restaurantURL;
    @SerializedName("Restaurant_Address")
    @Expose
    private String restaurantAddress;
    @SerializedName("Restaurant_Zipcode")
    @Expose
    private String restaurantZipcode;
    @SerializedName("Restaurant_Coordinates")
    @Expose
    private CurateAPICoordinates restaurantCoordinates;
    @SerializedName("Restaurant_CuisineTags")
    @Expose
    private Object restaurantCuisineTags;
    @SerializedName("Restaurant_PhoneNumber")
    @Expose
    private String restaurantPhoneNumber;
    @SerializedName("Restaurant_LogoURL")
    @Expose
    private String restaurantLogoURL;
    @SerializedName("Restaurant_NumberOfItemRatings")
    @Expose
    private Object restaurantNumberOfItemRatings;
    @SerializedName("Restaurant_SumOfItemRatings")
    @Expose
    private Object restaurantSumOfItemRatings;
    @SerializedName("Restaurant_MealTaxRate")
    @Expose
    private Double restaurantMealTaxRate;
    @SerializedName("Restaurant_RestaurantManagerID")
    @Expose
    private String restaurantRestaurantManagerID;
    @SerializedName("Restaurant_StripeID")
    @Expose
    private String restaurantStripeID;
    @SerializedName("Menus")
    @Expose
    private List<CurateAPIMenu> menus = null;

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    public String getRestaurantURL() {
        return restaurantURL;
    }

    public void setRestaurantURL(String restaurantURL) {
        this.restaurantURL = restaurantURL;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantZipcode() {
        return restaurantZipcode;
    }

    public void setRestaurantZipcode(String restaurantZipcode) {
        this.restaurantZipcode = restaurantZipcode;
    }

    public CurateAPICoordinates getRestaurantCoordinates() {
        return restaurantCoordinates;
    }

    public void setRestaurantCoordinates(CurateAPICoordinates restaurantCoordinates) {
        this.restaurantCoordinates = restaurantCoordinates;
    }

    public Object getRestaurantCuisineTags() {
        return restaurantCuisineTags;
    }

    public void setRestaurantCuisineTags(Object restaurantCuisineTags) {
        this.restaurantCuisineTags = restaurantCuisineTags;
    }

    public String getRestaurantPhoneNumber() {
        return restaurantPhoneNumber;
    }

    public void setRestaurantPhoneNumber(String restaurantPhoneNumber) {
        this.restaurantPhoneNumber = restaurantPhoneNumber;
    }

    public String getRestaurantLogoURL() {
        return restaurantLogoURL;
    }

    public void setRestaurantLogoURL(String restaurantLogoURL) {
        this.restaurantLogoURL = restaurantLogoURL;
    }

    public Object getRestaurantNumberOfItemRatings() {
        return restaurantNumberOfItemRatings;
    }

    public void setRestaurantNumberOfItemRatings(Object restaurantNumberOfItemRatings) {
        this.restaurantNumberOfItemRatings = restaurantNumberOfItemRatings;
    }

    public Object getRestaurantSumOfItemRatings() {
        return restaurantSumOfItemRatings;
    }

    public void setRestaurantSumOfItemRatings(Object restaurantSumOfItemRatings) {
        this.restaurantSumOfItemRatings = restaurantSumOfItemRatings;
    }

    public Double getRestaurantMealTaxRate() {
        return restaurantMealTaxRate;
    }

    public void setRestaurantMealTaxRate(Double restaurantMealTaxRate) {
        this.restaurantMealTaxRate = restaurantMealTaxRate;
    }

    public String getRestaurantRestaurantManagerID() {
        return restaurantRestaurantManagerID;
    }

    public void setRestaurantRestaurantManagerID(String restaurantRestaurantManagerID) {
        this.restaurantRestaurantManagerID = restaurantRestaurantManagerID;
    }

    public String getRestaurantStripeID() {
        return restaurantStripeID;
    }

    public void setRestaurantStripeID(String restaurantStripeID) {
        this.restaurantStripeID = restaurantStripeID;
    }

    public List<CurateAPIMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<CurateAPIMenu> menus) {
        this.menus = menus;
    }

}