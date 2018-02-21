package curatetechnologies.com.curate.domain.model;

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

    public RestaurantModel(Integer id, String name, String logoURL, String distance_in_mi,
                           List<MenuModel> menus) {
        this.id = id;
        this.name = name;
        this.logoURL = logoURL;
        this.distance_in_mi = distance_in_mi;
        this.menus = menus;
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
}
