package curatetechnologies.com.curate_consumer.storage;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;

/**
 * Created by mremondi on 2/23/18.
 */

public interface MenuModelRepository {

    MenuModel getMenuById(Integer menuId);
}
