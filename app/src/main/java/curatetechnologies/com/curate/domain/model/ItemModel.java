package curatetechnologies.com.curate.domain.model;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemModel{

    private Integer id;
    private String name;
    private String description;
    private String imageURL;
    private String distance_in_mi;
    private String price;
    private String rating;
    private String restaurantName;
    private Integer restaurantId;
    private String menuName;
    private Integer menuId;
    private String menuSectionName;

    public ItemModel(Integer id, String name, String description, String imageURL, String distance_in_mi,
                     String price, String rating, String restaurantName, String menuName,
                     String menuSectionName, Integer restaurantId, Integer menuId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.distance_in_mi = distance_in_mi;
        this.price = price;
        this.rating = rating;
        this.restaurantName = restaurantName;
        this.menuName = menuName;
        this.menuSectionName = menuSectionName;
        this.restaurantId = restaurantId;
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDistance_in_mi() {
        return distance_in_mi;
    }

    public void setDistance_in_mi(String distance_in_mi) {
        this.distance_in_mi = distance_in_mi;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuSectionName() {
        return menuSectionName;
    }

    public void setMenuSectionName(String menuSectionName) {
        this.menuSectionName = menuSectionName;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
