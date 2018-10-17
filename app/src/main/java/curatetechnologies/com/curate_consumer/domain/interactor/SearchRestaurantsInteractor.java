package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/12/18.
 */

public interface SearchRestaurantsInteractor extends Interactor {

    interface Callback {

        void onSearchRestaurantsRetrieved(List<RestaurantModel> restaurants);
        void onRetrievalFailed(String error);
    }
}
