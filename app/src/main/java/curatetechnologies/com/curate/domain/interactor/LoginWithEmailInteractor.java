package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;

/**
 * Created by mremondi on 2/13/18.
 */

public interface LoginWithEmailInteractor extends Interactor{

    interface Callback {

        void onLoginJWTRetrieved(String jwt);

        void onLoginRetrievalFailed(String error);
    }
}
