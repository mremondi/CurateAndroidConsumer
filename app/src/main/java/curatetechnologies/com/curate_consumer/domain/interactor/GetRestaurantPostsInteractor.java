package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;

/**
 * Created by mremondi on 3/1/18.
 */

public interface GetRestaurantPostsInteractor extends Interactor {
    interface Callback {

        void onPostsRetrieved(List<PostModel> posts);

        void onRetrievalFailed(String error);
    }
}
