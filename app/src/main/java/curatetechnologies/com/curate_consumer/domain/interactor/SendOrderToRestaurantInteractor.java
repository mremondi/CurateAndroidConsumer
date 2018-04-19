package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 3/26/18.
 */

public interface SendOrderToRestaurantInteractor extends Interactor {

    interface Callback {

        void onOrderSent();

        void onRetrievalFailed(String error);
    }
}
