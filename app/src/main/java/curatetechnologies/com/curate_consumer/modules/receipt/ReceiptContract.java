package curatetechnologies.com.curate_consumer.modules.receipt;

import android.content.Context;

import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/20/18.
 */

public interface ReceiptContract {

    interface View extends BaseView {
        void displayRestaurant(RestaurantModel restaurant);
    }

    void getRestaurantById(Integer restaurantId);
}