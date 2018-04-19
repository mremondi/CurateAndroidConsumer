package curatetechnologies.com.curate_consumer.domain.model;

import java.util.List;

/**
 * Created by mremondi on 2/23/18.
 */

public class MenuSectionModel {

    private Integer id;
    private String name;
    private List<ItemModel> items;

    public MenuSectionModel(Integer id, String name, List<ItemModel> items) {
        this.id = id;
        this.name = name;
        this.items = items;
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

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
