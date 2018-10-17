package curatetechnologies.com.curate_consumer.network.Builders;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetRestaurantByIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.fragment.RestaurantLocation;

public class GetRestaurantByIdBuilder {

    public static RestaurantModel buildRestaurant(GetRestaurantByIdQuery.Data data) {
        RestaurantModel restaurantModel = null;


        //TODO: Is this safe? Should we catch this?
        GetRestaurantByIdQuery.Restaurant restaurant = data.restaurants().get(0);
        RestaurantLocation restaurantLocation = restaurant.fragments().restaurantLocation();
        LatLng mapsLocation = new LatLng(restaurantLocation.latitude(), restaurantLocation.longitude());
        List<MenuModel> menus = buildMenus(restaurant);

        restaurantModel = new RestaurantModel(restaurant.id(), restaurant.name(), restaurant.logoUrl(),
                String.format("%.2f", restaurant.distance()) + " mi", menus, restaurant.mealTaxRate(), null,
                restaurant.url(), mapsLocation, restaurantLocation.address(), restaurant.stripeId(),
                restaurant.rating());

        return restaurantModel;
    }

    private static List<MenuModel> buildMenus(GetRestaurantByIdQuery.Restaurant restaurant) {
        List<MenuModel> menusModel = new ArrayList<>();

        for (GetRestaurantByIdQuery.Menus menu : restaurant.menus()) {

            /*TODO: NULL for now. My idea is: When we click on the menu, GetMenuById will be called.
            * WHICH MEANS that any section building in GetRestaurantById is unnecessary work.
            *  Still need to test it out though.
            * */

            MenuModel menuModel = new MenuModel(menu.id(), menu.name(), restaurant.id(), null);
            menusModel.add(menuModel);
        }

        return menusModel;
    }

}
