package curatetechnologies.com.curate.domain.model;

import java.util.List;

/**
 * Created by mremondi on 2/21/18.
 */

public class MenuModel {

    Integer id;
    String name;
    Integer restaurantId;
    List<MenuSectionModel> menuSections;

    public MenuModel(Integer id, String name, Integer restaurantId, List<MenuSectionModel> menuSections) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.menuSections = menuSections;
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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<MenuSectionModel> getMenuSections() {
        return menuSections;
    }

    public void setMenuSections(List<MenuSectionModel> menuSections) {
        this.menuSections = menuSections;
    }
}
