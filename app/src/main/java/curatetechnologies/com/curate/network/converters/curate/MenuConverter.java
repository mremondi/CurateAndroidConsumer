package curatetechnologies.com.curate.network.converters.curate;

import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.network.model.CurateAPIMenu;

/**
 * Created by mremondi on 2/21/18.
 */

public class MenuConverter {

    public static MenuModel convertCurateMenuToMenuModel(CurateAPIMenu apiMenu){
        return new MenuModel(apiMenu.getMenuID(), apiMenu.getMenuName());
    }
}
