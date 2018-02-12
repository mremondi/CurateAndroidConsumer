package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/12/18.
 */

public interface SearchRestaurantsInteractor extends Interactor {

    interface Callback {

        void onSearchRestaurantsRetrieved(List<RestaurantModel> restaurants);

        void onRetrievalFailed(String error);
    }
}
