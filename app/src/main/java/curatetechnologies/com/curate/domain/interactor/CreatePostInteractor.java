package curatetechnologies.com.curate.domain.interactor;

/**
 * Created by mremondi on 4/4/18.
 */

public interface CreatePostInteractor extends Interactor {

    interface Callback {
        void onCreatePostSuccess();
        void onCreatePostFailed(String error);
    }
}
