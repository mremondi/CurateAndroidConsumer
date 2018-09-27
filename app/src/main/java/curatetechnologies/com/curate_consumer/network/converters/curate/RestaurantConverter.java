package curatetechnologies.com.curate_consumer.network.converters.curate;

import android.util.Log;

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
        if (apiMenus != null){
            for (CurateAPIMenu apiMenu: apiMenus){
                menus.add(MenuConverter.convertCurateMenuToMenuModel(apiMenu));
            }
        }

        CurateAPICoordinates coordinates = apiRestaurant.getRestaurantCoordinates();
        LatLng latLng = new LatLng(coordinates.getX(), coordinates.getY());

        Double rating = 0.0;
        if (apiRestaurant.getRestaurantSumOfItemRatings() != null && apiRestaurant.getRestaurantNumberOfItemRatings() != null
                && apiRestaurant.getRestaurantNumberOfItemRatings() != 0) {
            rating = apiRestaurant.getRestaurantSumOfItemRatings().doubleValue() / apiRestaurant.getRestaurantNumberOfItemRatings();
        }
        return new RestaurantModel(apiRestaurant.getRestaurantID(), apiRestaurant.getRestaurantName(),
                apiRestaurant.getRestaurantLogoURL(), "0.0mi",
                menus, apiRestaurant.getRestaurantMealTaxRate(), apiRestaurant.getRestaurantPhoneNumber(),
                apiRestaurant.getRestaurantURL(), latLng, apiRestaurant.getRestaurantAddress(),
                apiRestaurant.getRestaurantStripeID(),  rating);
    }
}
