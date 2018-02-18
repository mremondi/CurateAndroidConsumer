package curatetechnologies.com.curate.domain.interactor;

/**
 * Created by mremondi on 2/18/18.
 */

public interface CheckUsernameAvailabilityInteractor extends Interactor {

    interface Callback {

        void onAvailableRetrieved(boolean available);

        void onRetrievalFailed(String error);
    }
}
