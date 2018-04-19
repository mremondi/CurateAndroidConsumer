package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;

/**
 * Created by mremondi on 2/10/18.
 */

public interface GetItemByIdInteractor extends Interactor {

    interface Callback {

        void onGetItemByIdRetrieved(ItemModel item);

        void onRetrievalFailed(String error);
    }
}