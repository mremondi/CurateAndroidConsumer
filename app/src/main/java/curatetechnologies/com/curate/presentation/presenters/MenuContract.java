package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/23/18.
 */

public interface MenuContract {

    interface View extends BaseView {
        void displayMenu(MenuModel menu);
    }

    void getMenuById(Integer menuId);
}
