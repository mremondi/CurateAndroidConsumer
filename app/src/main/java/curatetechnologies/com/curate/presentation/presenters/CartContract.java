package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/26/18.
 */

public interface CartContract {

    interface View extends BaseView {
        void displayRestaurant(RestaurantModel restaurant);
    }

    void getRestaurantById(Integer restaurantId);
}