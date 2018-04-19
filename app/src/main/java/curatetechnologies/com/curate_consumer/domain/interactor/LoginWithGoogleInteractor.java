package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginWithGoogleInteractor extends Interactor{

    interface Callback {

        void onLoginWithGoogle(String jwt);

        void onLoginWithGoogleFailed(String error);
    }
}
