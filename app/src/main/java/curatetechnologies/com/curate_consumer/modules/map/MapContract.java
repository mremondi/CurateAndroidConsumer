package curatetechnologies.com.curate_consumer.modules.map;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MapContract {

    interface View extends BaseView {
        void displayRestaurants(List<RestaurantModel> restaurants);
    }

    void getNearbyRestaurants(Location location, Float radius);
}
