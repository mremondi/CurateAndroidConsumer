package curatetechnologies.com.curate_consumer.network;


import android.location.Location;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetItemByIDQuery;
import curatetechnologies.com.curate_consumer.graphql.api.GetMenuByIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.GetRestaurantByIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.type.UserLocation;
import curatetechnologies.com.curate_consumer.network.Builders.GetItemByIdBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetMenuByIdBuilder;
import curatetechnologies.com.curate_consumer.network.Builders.GetRestaurantByIdBuilder;

public class CurateAPIClient implements CurateAPI{

    public static final String BASE_URL = "http://curate-staging.appspot.com/graphql";

    private ApolloClient mClient;

    public CurateAPIClient(){
        mClient = ApolloClient.builder().serverUrl(BASE_URL).build();
    }

    @Override
    public void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId, Location location, int radius){
        UserLocation userLocation = UserLocation.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .radiusLimit(radius)
                .build();

        mClient.query(GetItemByIDQuery.builder()
                .itemID(itemId)
                .userLocation(userLocation)
                .build())
                .enqueue(new ApolloCall.Callback<GetItemByIDQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetItemByIDQuery.Data> response) {
                        ItemModel itemModel = GetItemByIdBuilder.buildItem(response.data());
                        itemModelRepository.onItemRetrieved(itemModel);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        itemModelRepository.onFailure(e.getMessage());
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
                                  int restaurantID, Location location, int radius) {

        UserLocation userLocation = UserLocation.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .radiusLimit(radius)
                .build();

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
                        restaurantModelRepository.onFailure(e.getMessage());
                    }
                });
    }

}
