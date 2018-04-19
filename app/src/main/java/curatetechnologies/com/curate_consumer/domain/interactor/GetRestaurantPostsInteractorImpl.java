package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

/**
 * Created by mremondi on 3/1/18.
 */

public class GetRestaurantPostsInteractorImpl  extends AbstractInteractor implements GetRestaurantPostsInteractor {

    private GetRestaurantPostsInteractor.Callback mCallback;
    private PostModelRepository mPostRepository;

    private Integer mLimit;
    private Integer mRestaurantId;

    public GetRestaurantPostsInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          GetRestaurantPostsInteractor.Callback callback,
                                          PostModelRepository postModelRepository,
                                          Integer limit,
                                          Integer restaurantId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mPostRepository = postModelRepository;
        mLimit = limit;
        mRestaurantId = restaurantId;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Get Posts By UserId Failed");
            }
        });
    }

    private void postPosts(final List<PostModel> postModels) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostsRetrieved(postModels);
            }
        });
    }


    @Override
    public void run() {
        final List<PostModel> posts = mPostRepository.getPostsByRestaurantId(mLimit, mRestaurantId, "ImageRating");

        // check if we have failed to retrieve our message
        if (posts == null || posts.size() == 0) {
            // notify the failure on the main thread
            notifyError();

            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postPosts(posts);
    }
}
