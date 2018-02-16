package curatetechnologies.com.curate.domain.model;

/**
 * Created by mremondi on 2/16/18.
 */

public class TagTypeModel {

    private Integer id;
    private String type;
    private String imageURL;

    public TagTypeModel(Integer id, String type, String imageURL) {
        this.id = id;
        this.type = type;
        this.imageURL = imageURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
