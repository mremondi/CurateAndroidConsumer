package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

/**
 * Created by mremondi on 2/22/18.
 */

public class GetPostsByLocationInteractorImpl extends AbstractInteractor implements GetPostsByLocationInteractor {

    private GetPostsByLocationInteractor.Callback mCallback;
    private PostModelRepository mPostRepository;

    private Integer mLimit;
    private Location mLocation;
    private Float mRadius;

    public GetPostsByLocationInteractorImpl(Executor threadExecutor,
                                            MainThread mainThread,
                                            GetPostsByLocationInteractor.Callback callback,
                                            PostModelRepository itemModelRepository,
                                            Integer limit,
                                            Location location,
                                            Float radius) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mPostRepository = itemModelRepository;
        mLimit = limit;
        mLocation = location;
        mRadius = radius;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Search Item Failed");
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
        final List<PostModel> posts = mPostRepository.getPostsByLocation(mLimit, mLocation, mRadius);

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