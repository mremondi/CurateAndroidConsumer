package curatetechnologies.com.curate.domain.interactor;

import android.util.Pair;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/13/18.
 */

public interface CreateAccountWithEmailInteractor extends Interactor{

    interface Callback {

        void onLoginUserRetrieved(UserModel user);

        void onLoginRetrievalFailed(String error);
    }
}
