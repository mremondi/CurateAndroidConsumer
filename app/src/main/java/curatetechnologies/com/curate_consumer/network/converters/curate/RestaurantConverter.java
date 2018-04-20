package curatetechnologies.com.curate_consumer.network.converters.curate;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.network.model.CurateAPICoordinates;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIMenu;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIRestaurant;

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

        CurateAPICoordinates coordinates = apiRestaurant.getRestaurantCoordinates();
        LatLng latLng = new LatLng(coordinates.getX(), coordinates.getY());

        return new RestaurantModel(apiRestaurant.getRestaurantID(), apiRestaurant.getRestaurantName(),
                apiRestaurant.getRestaurantLogoURL(), "0.0mi",
                menus, apiRestaurant.getRestaurantMealTaxRate(), apiRestaurant.getRestaurantPhoneNumber(),
                apiRestaurant.getRestaurantURL(), latLng, apiRestaurant.getRestaurantAddress());
    }
}
