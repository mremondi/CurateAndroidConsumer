package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;

/**
 * Created by mremondi on 4/4/18.
 */

public interface GetItemPostsInteractor extends Interactor {
    interface Callback {

        void onPostsRetrieved(List<PostModel> posts);

        void onRetrievalFailed(String error);
    }
}
