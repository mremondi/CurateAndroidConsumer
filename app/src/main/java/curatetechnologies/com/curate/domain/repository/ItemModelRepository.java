package curatetechnologies.com.curate.domain.repository;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;

/**
 * Created by mremondi on 2/9/18.
 */

public interface ItemModelRepository {

    List<ItemModel> searchItems(String query);
}
