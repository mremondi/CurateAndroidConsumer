package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;

/**
 * Created by mremondi on 2/10/18.
 */

public interface GetItemByIdInteractor extends Interactor {

    interface Callback {

        void onGetItemByIdRetrieved(ItemModel item);

        void onRetrievalFailed(String error);
    }
}