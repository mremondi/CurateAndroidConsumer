package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.OrderModel;

/**
 * Created by mremondi on 4/12/18.
 */

public interface GetLastOrderInteractor extends Interactor {
    interface Callback {
        void onOrderModelRetrieved(OrderModel orderModel);
        void onRetrievalFailed(String error);
    }
}