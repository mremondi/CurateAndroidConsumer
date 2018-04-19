package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

/**
 * Created by mremondi on 4/4/18.
 */

public class GetItemPostsInteractorImpl extends AbstractInteractor implements GetItemPostsInteractor {

    private GetItemPostsInteractor.Callback mCallback;
    private PostModelRepository mPostRepository;

    private Integer mLimit;
    private Integer mItemId;

    public GetItemPostsInteractorImpl(Executor threadExecutor,
                                            MainThread mainThread,
                                            GetItemPostsInteractor.Callback callback,
                                            PostModelRepository postModelRepository,
                                            Integer limit,
                                            Integer itemId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mPostRepository = postModelRepository;
        mLimit = limit;
        mItemId = itemId;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Get Posts By ItemId Failed");
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
        final List<PostModel> posts = mPostRepository.getPostsByItemId(mLimit, mItemId, "ImageRating");

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
