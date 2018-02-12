package curatetechnologies.com.curate.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurateAPIMenu {

    @SerializedName("Menu_ID")
    @Expose
    private Integer menuID;
    @SerializedName("Menu_Name")
    @Expose
    private String menuName;

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

}