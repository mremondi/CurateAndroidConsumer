package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurateAPIMenuSection {

    @SerializedName("MenuSection_ID")
    @Expose
    private Integer menuSectionID;
    @SerializedName("MenuSection_Name")
    @Expose
    private String menuSectionName;
    @SerializedName("Items")
    @Expose
    private List<CurateAPIItem> items = null;

    public Integer getMenuSectionID() {
        return menuSectionID;
    }

    public void setMenuSectionID(Integer menuSectionID) {
        this.menuSectionID = menuSectionID;
    }

    public String getMenuSectionName() {
        return menuSectionName;
    }

    public void setMenuSectionName(String menuSectionName) {
        this.menuSectionName = menuSectionName;
    }

    public List<CurateAPIItem> getItems() {
        return items;
    }

    public void setItems(List<CurateAPIItem> items) {
        this.items = items;
    }
}
