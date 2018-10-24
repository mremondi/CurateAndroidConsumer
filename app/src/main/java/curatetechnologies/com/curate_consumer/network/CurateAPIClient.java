package curatetechnologies.com.curate_consumer.network;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetItemByIDQuery;
import curatetechnologies.com.curate_consumer.graphql.api.GetMenuByIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.GetNearbyRestaurantsQuery;
import curatetechnologies.com.curate_consumer.graphql.api.GetPostsByUserIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.GetRestaurantByIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.IsUserNameAvailableQuery;
import curatetechnologies.com.curate_consumer.graphql.api.LoadFeedQuery;
import curatetechnologies.com.curate_consumer.graphql.api.SearchItemsQuery;
import curatetechnologies.com.curate_consumer.graphql.api.SearchRestaurantQuery;
import curatetechnologies.com.curate_consumer.graphql.api.type.UserLocation;
import curatetechnologies.com.curate_consumer.network.Builders.GetItemByIdBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetMenuByIdBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetNearbyRestaurantsBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetPostsByLocationBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetPostsByUserIdBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetRestaurantByIdBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.SearchItemsBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.SearchRestaurantsBuilder;

public class CurateAPIClient implements CurateAPI {

    public static final String BASE_URL = "http://curate-staging.appspot.com/graphql";

    private ApolloClient mClient;

    public CurateAPIClient(){
        mClient = ApolloClient.builder().serverUrl(BASE_URL).build();
    }

    @Override
    public void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId,
                            float lat, float lon, int radius){
        UserLocation userLocation = buildUserLocation(lat, lon, radius);

        mClient.query(GetItemByIDQuery.builder()
                .itemID(itemId)
                .userLocation(userLocation)
                .build())
                .enqueue(new ApolloCall.Callback<GetItemByIDQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetItemByIDQuery.Data> response) {
                        ItemModel itemModel = GetItemByIdBuilder.buildItem(response.data());
                        System.out.println("about to call item repo");
                        itemModelRepository.onItemRetrieved(itemModel);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println("failure of call");
                        itemModelRepository.onGetItemByIdFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void getMenuById(GetMenuByIdCallback menuModelRepository, int menuId) {
       mClient.query(GetMenuByIdQuery.builder()
               .menuID(menuId)
               .build())
               .enqueue(new ApolloCall.Callback<GetMenuByIdQuery.Data>() {
                   @Override
                   public void onResponse(@NotNull Response<GetMenuByIdQuery.Data> response) {
                       MenuModel menuModel = GetMenuByIdBuilder.buildMenu(response.data());
                       menuModelRepository.onMenuRetrieved(menuModel);
                   }

                   @Override
                   public void onFailure(@NotNull ApolloException e) {
                        menuModelRepository.onFailure(e.getMessage());
                   }
               });
    }

    public void getRestaurantById(final CurateAPI.GetRestaurantByIdCallback restaurantModelRepository,
                                  int restaurantID, float lat, float lon, int radius) {

        UserLocation userLocation = buildUserLocation(lat, lon, radius);

        mClient.query(GetRestaurantByIdQuery.builder()
                .restaurantID(restaurantID)
                .userLocation(userLocation)
                .build())
                .enqueue(new ApolloCall.Callback<GetRestaurantByIdQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetRestaurantByIdQuery.Data> response) {
                        RestaurantModel restaurantModel =
                                GetRestaurantByIdBuilder.buildRestaurant(response.data());

                        restaurantModelRepository.onRestaurantRetrieved(restaurantModel);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        restaurantModelRepository.onGetRestaurantByIdFailure(e.getMessage());
                    }
                });
    }

    public void searchItems(final CurateAPI.SearchItemsCallback itemModelRepository, String query,
                            float lat, float lon, int radius) {

        UserLocation userLocation = buildUserLocation(lat, lon,radius);

        mClient.query(SearchItemsQuery.builder()
                .itemName(query)
                .userLocation(userLocation).build())
                .enqueue(new ApolloCall.Callback<SearchItemsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<SearchItemsQuery.Data> response) {
                        List<ItemModel> itemModels = SearchItemsBuilder.buildItems(response.data());
                        itemModelRepository.onItemsRetrieved(itemModels);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        itemModelRepository.onSearchItemsFailure(e.getMessage());
                    }
                });
    }

    public void  searchRestaurants(final CurateAPI.SearchRestaurantsCallback restaurantModelRepository,
                                       String query, float lat, float lon, int radius) {

        UserLocation userLocation = buildUserLocation(lat, lon, radius);

        mClient.query(SearchRestaurantQuery.builder()
                .restaurantName(query)
                .userLocation(userLocation)
                .build())
                .enqueue(new ApolloCall.Callback<SearchRestaurantQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<SearchRestaurantQuery.Data> response) {
                        List<RestaurantModel> restaurantModels =
                                SearchRestaurantsBuilder.buildRestaurants(response.data());
                        restaurantModelRepository.onRestaurantsRetrieved(restaurantModels);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        restaurantModelRepository.onSearchRestaurantsFailure(e.getMessage());
                    }
                });

    }

    public void getNearbyRestaurants(final CurateAPI.GetNearbyRestaurantsCallback restaurantModelRepository,
                                     float lat, float lon, int radius) {
        UserLocation userLocation = buildUserLocation(lat, lon, radius);

        mClient.query(GetNearbyRestaurantsQuery.builder()
                .location(userLocation)
                .build())
                .enqueue(new ApolloCall.Callback<GetNearbyRestaurantsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetNearbyRestaurantsQuery.Data> response) {
                        List<RestaurantModel> restaurantModels = GetNearbyRestaurantsBuilder
                                .buildRestaurants(response.data());
                        restaurantModelRepository.onNearbyRestaurantsRetrieved(restaurantModels);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        restaurantModelRepository.onGetNearbyRestaurantsFailure(e.getMessage());
                    }
                });
    }


    @Override
    public void getPostsByLocation(GetPostsByLocationCallback postModelRepository, int limit,
                                   float lat, float lon, int radius) {

        UserLocation userLocation = buildUserLocation(lat, lon, radius);

        mClient.query(LoadFeedQuery.builder()
                .limit(limit)
                .userLocation(userLocation)
                .build())
                .enqueue(new ApolloCall.Callback<LoadFeedQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoadFeedQuery.Data> response) {
                        List<PostModel> postModels = GetPostsByLocationBuilder.buildPosts(response.data());
                        postModelRepository.onPostsRetrieved(postModels);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        postModelRepository.onPostsByLocationFailure(e.getMessage());
                    }
                });

    }

    public void getPostsByUserId(final CurateAPI.GetPostsByUserIdCallback postModelRepository, int limit,
                                 int userID) {

        mClient.query(GetPostsByUserIdQuery.builder()
                .userId(userID)
                .build())
                .enqueue(new ApolloCall.Callback<GetPostsByUserIdQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetPostsByUserIdQuery.Data> response) {
                        List<PostModel> postModels = GetPostsByUserIdBuilder.buildPosts(response.data());
                        postModelRepository.onUserPostsRetrieved(postModels);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        postModelRepository.onPostsByUserIdFailure(e.getMessage());
                    }
                });
    }

    public void isUsernameAvailable(final CurateAPI.IsUsernameAvailableCallback userModelRepository,
                                    String username) {

        mClient.query(IsUserNameAvailableQuery.builder()
                .username(username)
                .build())
                .enqueue(new ApolloCall.Callback<IsUserNameAvailableQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<IsUserNameAvailableQuery.Data> response) {
                        boolean available = response.data().userNameAvailable();
                        userModelRepository.onUsernameAvailabilityRetrieved(available);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        userModelRepository.onFailure(e.getMessage());
                    }
                });

    }

    private UserLocation buildUserLocation(float lat, float lon, int radius) {
        UserLocation userLocation = UserLocation.builder()
                .latitude(lat)
                .longitude(lon)
                .radiusLimit(radius)
                .build();

        return userLocation;
    }

}
