package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;

/**
 * Created by mremondi on 3/22/18.
 */

public interface GetUserByIDInteractor extends Interactor {

    interface Callback {

        void onUserRetrieved(UserModel userModel);

        void onRetrievalFailed(String error);
    }
}
