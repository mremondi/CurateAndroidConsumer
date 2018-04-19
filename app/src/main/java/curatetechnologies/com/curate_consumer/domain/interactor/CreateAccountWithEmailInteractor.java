package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;

/**
 * Created by mremondi on 2/13/18.
 */

public interface CreateAccountWithEmailInteractor extends Interactor{

    interface Callback {

        void onRegisterUser(UserModel user);

        void onRegisterFailed(String error);
    }
}
