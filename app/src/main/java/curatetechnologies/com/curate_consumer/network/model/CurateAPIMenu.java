package curatetechnologies.com.curate_consumer.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurateAPIMenu {

    @SerializedName("Menu_ID")
    @Expose
    private Integer menuID;
    @SerializedName("Menu_Name")
    @Expose
    private String menuName;
    @SerializedName("Restaurant_ID")
    @Expose
    private Integer restaurantID;
    @SerializedName("MenuSections")
    @Expose
    private List<CurateAPIMenuSection> menuSections;

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public List<CurateAPIMenuSection> getMenuSections() {
        return menuSections;
    }

    public void setMenuSections(List<CurateAPIMenuSection> menuSections) {
        this.menuSections = menuSections;
    }

}