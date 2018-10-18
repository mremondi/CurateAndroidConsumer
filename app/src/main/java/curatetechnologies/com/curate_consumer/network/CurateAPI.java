package curatetechnologies.com.curate_consumer.network;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

public interface CurateAPI {

    void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId, Location location, int radius);
    void getMenuById(final CurateAPI.GetMenuByIdCallback menuModelRepository, int menuId);
    void getRestaurantById(final CurateAPI.GetRestaurantByIdCallback restaurantModelRepository,
                           int restaurantID, Location location, int radius);
    void searchItems(final CurateAPI.SearchItemsCallback itemModelRepository, String query,
                     Location location, Float radius);
    void searchRestaurants(final CurateAPI.SearchRestaurantsCallback restaurantModelRepository,
                           String query, Location location, Float radius);
    void getNearbyRestaurants(final CurateAPI.GetNearbyRestaurantsCallback restaurantModelRepository,
                              Location location, Float radius);
    void getPostsByLocation(final CurateAPI.GetPostsByLocationCallback postModelRepository, int limit,
                            Location location, Float radius);

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

    interface SearchItemsCallback {
        void onItemsRetrieved(List<ItemModel> itemModels);
        void onFailure(String message);
    }

    interface SearchRestaurantsCallback {
        void onRestaurantsRetrieved(List<RestaurantModel> restaurantModels);
        void onFailure(String message);
    }

    interface GetNearbyRestaurantsCallback {
        void onNearbyRestaurantsRetrieved(List<RestaurantModel> restaurantModels);
        void onFailure(String message);
    }

    interface GetPostsByLocationCallback {
        void onPostsRetrieved(List<PostModel> postModels);
        void onFailure(String message);
    }

}
