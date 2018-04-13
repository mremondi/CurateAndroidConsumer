package curatetechnologies.com.curate.domain.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantModel {
    private Integer id;
    private String name;
    private String logoURL;
    private String distance_in_mi;
    private List<MenuModel> menus;
    private Double mealTaxRate;
    private String phoneNumber;
    private String websiteURL;
    private LatLng restaurantLocation;

    public RestaurantModel(Integer id, String name, String logoURL, String distance_in_mi,
                           List<MenuModel> menus, Double mealTaxRate, String phoneNumber,
                           String websiteURL, LatLng restaurantLocation) {
        this.id = id;
        this.name = name;
        this.logoURL = logoURL;
        this.distance_in_mi = distance_in_mi;
        this.menus = menus;
        this.mealTaxRate = mealTaxRate;
        this.phoneNumber = phoneNumber;
        this.websiteURL = websiteURL;
        this.restaurantLocation = restaurantLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getDistance_in_mi() {
        return distance_in_mi;
    }

    public void setDistance_in_mi(String distance_in_mi) {
        this.distance_in_mi = distance_in_mi;
    }

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }

    public Double getMealTaxRate() {
        return mealTaxRate;
    }

    public void setMealTaxRate(Double mealTaxRate) {
        this.mealTaxRate = mealTaxRate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public LatLng getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(LatLng restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }
}
