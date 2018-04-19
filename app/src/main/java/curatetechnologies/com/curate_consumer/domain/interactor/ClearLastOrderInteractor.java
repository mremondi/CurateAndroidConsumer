package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 4/12/18.
 */

public interface ClearLastOrderInteractor extends Interactor {
    interface Callback {
        void onOrderCleared();
    }
}
