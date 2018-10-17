package curatetechnologies.com.curate_consumer.network;

import android.location.Location;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

public interface CurateAPI {

    void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId, Location location, int radius);
    void getMenuById(final CurateAPI.GetMenuByIdCallback menuModelRepository, int menuId);
    void getRestaurantById(final CurateAPI.GetRestaurantByIdCallback restaurantModelRepository,
                           int restaurantID, Location location, int radius);

    interface GetItemByIdCallback {
        void onItemRetrieved(ItemModel itemModel);
        void onFailure(String message);
    }

    interface GetMenuByIdCallback {
        void onMenuRetrieved(MenuModel menuModel);
        void onFailure(String message);
    }

    interface GetRestaurantByIdCallback {
        void onRestaurantRetrieved(RestaurantModel restaurantModel);
        void onFailure(String message);
    }
}
