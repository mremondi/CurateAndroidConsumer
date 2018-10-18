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

public class GetPostsByLocationInteractorImpl extends AbstractInteractor
        implements GetPostsByLocationInteractor, PostModelRepository.GetPostsByLocationCallback {

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


    // BEGIN PostModelRepository.GetPostsByLocationCallback Methods

    public void postPosts(final List<PostModel> postModels) {
        mMainThread.post(() -> {
            mCallback.onPostsRetrieved(postModels);
        });
    }

    public void notifyError(String message) {
        mMainThread.post(() -> {
            mCallback.onRetrievalFailed(message);
        });
    }


    // END PostModelRepository.GetPostsByLocationCallback Methods


    @Override
    public void run() {
        mPostRepository.getPostsByLocation(this, mLimit, mLocation, mRadius);
    }
}