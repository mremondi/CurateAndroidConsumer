package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

public interface GetNearbyRestaurantsInteractor extends Interactor{
    interface Callback {
        void onRestaurantsRetrieved(List<RestaurantModel> restaurants);
        void onRetrievalFailed(String error);
    }
}
