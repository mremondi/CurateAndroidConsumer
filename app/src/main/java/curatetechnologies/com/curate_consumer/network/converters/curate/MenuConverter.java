package curatetechnologies.com.curate_consumer.network.converters.curate;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuSectionModel;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIMenu;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIMenuSection;

/**
 * Created by mremondi on 2/21/18.
 */

public class MenuConverter {

    public static MenuModel convertCurateMenuToMenuModel(CurateAPIMenu apiMenu){
        if (apiMenu.getMenuSections() != null) {
            List<MenuSectionModel> sections = new ArrayList<>();
            for (CurateAPIMenuSection apiSection : apiMenu.getMenuSections()) {
                sections.add(MenuSectionConverter.convertCurateMenuSectionToMenuSectionModel(apiSection));
            }
            return new MenuModel(apiMenu.getMenuID(), apiMenu.getMenuName(), apiMenu.getRestaurantID(),
                    sections);
        } else{
            return new MenuModel(apiMenu.getMenuID(), apiMenu.getMenuName(), apiMenu.getRestaurantID(),
                    null);
        }
    }
}
