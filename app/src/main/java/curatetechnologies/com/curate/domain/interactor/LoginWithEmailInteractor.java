package curatetechnologies.com.curate.domain.interactor;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginWithEmailInteractor extends Interactor{

    interface Callback {

        void onLoginUserRetrieved(UserModel user);

        void onLoginRetrievalFailed(String error);
    }
}
