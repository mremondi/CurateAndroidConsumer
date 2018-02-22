package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;

/**
 * Created by mremondi on 2/22/18.
 */

public interface GetPostsByLocationInteractor extends Interactor {

    interface Callback {

        void onPostsRetrieved(List<PostModel> restaurant);

        void onRetrievalFailed(String error);
    }
}