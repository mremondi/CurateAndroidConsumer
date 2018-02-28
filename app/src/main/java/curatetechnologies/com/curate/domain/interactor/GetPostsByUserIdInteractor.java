package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;

/**
 * Created by mremondi on 2/28/18.
 */

public interface GetPostsByUserIdInteractor extends Interactor {
    interface Callback {

        void onPostsRetrieved(List<PostModel> restaurant);

        void onRetrievalFailed(String error);
    }
}
