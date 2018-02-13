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

    public ItemModel(Integer id, String name, String description, String imageURL, String distance_in_mi,
                     String price, String rating){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.distance_in_mi = distance_in_mi;
        this.price = price;
        this.rating = rating;
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
}
