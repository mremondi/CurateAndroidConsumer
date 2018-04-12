package curatetechnologies.com.curate.domain.interactor;

import curatetechnologies.com.curate.domain.model.OrderModel;

/**
 * Created by mremondi on 4/12/18.
 */

public interface GetLastOrderInteractor extends Interactor {
    interface Callback {
        void onOrderModelRetrieved(OrderModel orderModel);
        void onRetrievalFailed(String error);
    }
}