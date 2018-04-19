package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;

/**
 * Created by mremondi on 2/16/18.
 */

public interface GetUserInteractor extends Interactor {

    interface Callback {

        void onUserRetrieved(UserModel user);

        void onRetrieveUserFailed(String error);
    }
}
