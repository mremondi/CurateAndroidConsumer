package curatetechnologies.com.curate.domain.model;

/**
 * Created by mremondi on 2/21/18.
 */

public class MenuModel {

    Integer id;
    String name;

    public MenuModel(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}