package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;

/**
 * Created by mremondi on 2/23/18.
 */

public interface GetMenuByIdInteractor extends Interactor {

    interface Callback {

        void onMenuRetrieved(MenuModel menu);

        void onRetrievalFailed(String error);
    }
}