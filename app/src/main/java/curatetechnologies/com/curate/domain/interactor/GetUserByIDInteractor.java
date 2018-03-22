package curatetechnologies.com.curate.domain.interactor;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 3/22/18.
 */

public interface GetUserByIDInteractor extends Interactor {

    interface Callback {

        void onUserRetrieved(UserModel userModel);

        void onRetrievalFailed(String error);
    }
}
