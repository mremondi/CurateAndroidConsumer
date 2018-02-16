package curatetechnologies.com.curate.domain.interactor;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/16/18.
 */

public interface GetUserInteractor extends Interactor {

    interface Callback {

        void onUserRetrieved(UserModel user);

        void onRetrieveUserFailed(String error);
    }
}
