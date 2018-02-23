package curatetechnologies.com.curate.storage;

import curatetechnologies.com.curate.domain.model.MenuModel;

/**
 * Created by mremondi on 2/23/18.
 */

public interface MenuModelRepository {

    MenuModel getMenuById(Integer menuId);
}
