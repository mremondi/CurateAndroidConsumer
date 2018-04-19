package curatetechnologies.com.curate_consumer.domain.interactor;

/**
 * Created by mremondi on 4/4/18.
 */

public interface CreatePostInteractor extends Interactor {

    interface Callback {
        void onCreatePostSuccess();
        void onCreatePostFailed(String error);
    }
}
