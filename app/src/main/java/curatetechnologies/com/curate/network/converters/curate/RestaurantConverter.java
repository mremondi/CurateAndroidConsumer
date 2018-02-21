package curatetechnologies.com.curate.network.converters.curate;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.network.model.CurateAPIMenu;
import curatetechnologies.com.curate.network.model.CurateAPIRestaurant;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantConverter {

    public static RestaurantModel convertCurateRestaurantToRestaurantModel(CurateAPIRestaurant apiRestaurant){
        List<CurateAPIMenu> apiMenus = apiRestaurant.getMenus();
        List<MenuModel> menus = new ArrayList<>();
        for (CurateAPIMenu apiMenu: apiMenus){
            menus.add(MenuConverter.convertCurateMenuToMenuModel(apiMenu));
        }
        return new RestaurantModel(apiRestaurant.getRestaurantID(), apiRestaurant.getRestaurantName(),
                apiRestaurant.getRestaurantLogoURL(), "0.0mi",
                menus);
    }
}
