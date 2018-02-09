package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;

/**
 * Created by mremondi on 2/9/18.
 */

public interface SearchItemsInteractor extends Interactor {

    interface Callback {

        void onSearchItemsRetrieved(List<ItemModel> items);

        void onRetrievalFailed(String error);
    }
}
