package curatetechnologies.com.curate_consumer.modules.cart;

import android.content.Context;
import android.location.Location;

import com.stripe.android.PaymentSession;

import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/26/18.
 */

public interface CartContract {

    interface View extends BaseView {
        void displayRestaurant(RestaurantModel restaurant);
        void chargeCompleted(boolean success);
        void orderProcessed();
    }

    void getRestaurantById(Integer restaurantId, Location location, Float radiusMiles);

    void completeCharge(PaymentSession paymentSession, String email);

    void processOrder(String jwt, OrderModel orderModel, Context appContext);
}