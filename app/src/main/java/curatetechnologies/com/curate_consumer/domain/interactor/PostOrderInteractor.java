package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 3/26/18.
 */

public interface PostOrderInteractor extends Interactor {

    interface Callback {

        void onOrderPosted();

        void onOrderPostFailed(String error);
    }
}
