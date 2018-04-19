package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/21/18.
 */

public interface GetRestaurantByIdInteractor extends Interactor {

    interface Callback {

        void onRestaurantRetrieved(RestaurantModel restaurant);

        void onRetrievalFailed(String error);
    }
}