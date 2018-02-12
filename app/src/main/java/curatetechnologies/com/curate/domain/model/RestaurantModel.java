package curatetechnologies.com.curate.domain.model;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantModel {
    private Integer id;
    private String name;
    private String logoURL;

    public RestaurantModel(Integer id, String name, String logoURL) {
        this.id = id;
        this.name = name;
        this.logoURL = logoURL;
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
}
