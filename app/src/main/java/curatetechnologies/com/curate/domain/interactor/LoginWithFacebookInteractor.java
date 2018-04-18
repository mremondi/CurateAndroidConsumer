package curatetechnologies.com.curate.domain.interactor;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginWithFacebookInteractor extends Interactor{

    interface Callback {

        void onLoginWithFacebook(String jwt);

        void onLoginWithFacebookFailed(String error);
    }
}
