package curatetechnologies.com.curate.domain.model;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemModel{

    private Integer id;
    private String name;
    private String description;
    private String imageURL;

    public ItemModel(Integer id, String name, String description, String imageURL){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
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
}
