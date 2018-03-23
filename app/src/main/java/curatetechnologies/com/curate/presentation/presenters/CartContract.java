package curatetechnologies.com.curate.presentation.presenters;

import com.stripe.android.PaymentSession;

import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/26/18.
 */

public interface CartContract {

    interface View extends BaseView {
        void displayRestaurant(RestaurantModel restaurant);
        void chargeCompleted(boolean success);
    }

    void getRestaurantById(Integer restaurantId);

    void completeCharge(PaymentSession paymentSession, String email);
}