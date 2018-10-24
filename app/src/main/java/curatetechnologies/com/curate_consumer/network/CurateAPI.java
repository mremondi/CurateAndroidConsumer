package curatetechnologies.com.curate_consumer.network;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

public interface CurateAPI {

    void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId, float lat,
                     float lon, int radius);
    void getMenuById(final CurateAPI.GetMenuByIdCallback menuModelRepository, int menuId);
    void getRestaurantById(final CurateAPI.GetRestaurantByIdCallback restaurantModelRepository,
                           int restaurantID, float lat, float lon, int radius);
    void searchItems(final CurateAPI.SearchItemsCallback itemModelRepository, String query, float lat,
                     float lon, int radius);
    void searchRestaurants(final CurateAPI.SearchRestaurantsCallback restaurantModelRepository,
                           String query, float lat, float lon, int radius);
    void getNearbyRestaurants(final CurateAPI.GetNearbyRestaurantsCallback restaurantModelRepository,
                              float lat, float lon, int radius);
    void getPostsByLocation(final CurateAPI.GetPostsByLocationCallback postModelRepository, int limit,
                            float lat, float lon, int radius);
    void getPostsByUserId(final CurateAPI.GetPostsByUserIdCallback postModelRepository, int limit,
                          int userID);
    void isUsernameAvailable(final CurateAPI.IsUsernameAvailableCallback userModelRepository, String username);

    interface GetItemByIdCallback {
        void onItemRetrieved(ItemModel itemModel);
        void onGetItemByIdFailure(String message);
    }

    interface GetMenuByIdCallback {
        void onMenuRetrieved(MenuModel menuModel);
        void onFailure(String message);
    }

    interface GetRestaurantByIdCallback {
        void onRestaurantRetrieved(RestaurantModel restaurantModel);
        void onGetRestaurantByIdFailure(String message);
    }

    interface SearchItemsCallback {
        void onItemsRetrieved(List<ItemModel> itemModels);
        void onSearchItemsFailure(String message);
    }

    interface SearchRestaurantsCallback {
        void onRestaurantsRetrieved(List<RestaurantModel> restaurantModels);
        void onSearchRestaurantsFailure(String message);
    }

    interface GetNearbyRestaurantsCallback {
        void onNearbyRestaurantsRetrieved(List<RestaurantModel> restaurantModels);
        void onGetNearbyRestaurantsFailure(String message);
    }

    interface GetPostsByLocationCallback {
        void onPostsRetrieved(List<PostModel> postModels);
        void onPostsByLocationFailure(String message);
    }

    interface GetPostsByUserIdCallback {
        void onUserPostsRetrieved(List<PostModel> postModels);
        void onPostsByUserIdFailure(String message);
    }

    interface IsUsernameAvailableCallback {
        void onUsernameAvailabilityRetrieved(boolean available);
        void onFailure(String message);
    }

}
