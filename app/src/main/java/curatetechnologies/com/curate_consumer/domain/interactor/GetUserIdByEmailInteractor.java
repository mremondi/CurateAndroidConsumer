package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 4/17/18.
 */

public interface GetUserIdByEmailInteractor extends Interactor {

    interface Callback {

        void onUserIdRetrieved(Integer userId);
        void onRetrieveUserIdFailed(String error);
    }
}
