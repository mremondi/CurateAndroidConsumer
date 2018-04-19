package curatetechnologies.com.curate_consumer.modules.menu;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/23/18.
 */

public interface MenuContract {

    interface View extends BaseView {
        void displayMenu(MenuModel menu);
    }

    void getMenuById(Integer menuId);
}
