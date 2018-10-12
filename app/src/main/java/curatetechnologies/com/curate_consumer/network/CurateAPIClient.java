package curatetechnologies.com.curate_consumer.network;


import android.location.Location;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetItemByIDQuery;
import curatetechnologies.com.curate_consumer.graphql.api.type.UserLocation;

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
c
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


}
