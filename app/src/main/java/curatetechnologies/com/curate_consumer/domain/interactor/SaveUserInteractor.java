package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 2/15/18.
 */

public interface SaveUserInteractor extends Interactor {

        interface Callback {

            void onUserSaved();

            void onSaveFailed(String error);
        }
}
