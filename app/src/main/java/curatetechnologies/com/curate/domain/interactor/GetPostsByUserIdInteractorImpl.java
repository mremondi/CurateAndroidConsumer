package curatetechnologies.com.curate.domain.interactor;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.storage.PostModelRepository;

/**
 * Created by mremondi on 2/28/18.
 */

public class GetPostsByUserIdInteractorImpl extends AbstractInteractor implements GetPostsByUserIdInteractor {

    private GetPostsByUserIdInteractor.Callback mCallback;
    private PostModelRepository mPostRepository;

    private Integer mLimit;
    private Integer mUserId;

    public GetPostsByUserIdInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          GetPostsByUserIdInteractor.Callback callback,
                                          PostModelRepository itemModelRepository,
                                          Integer limit,
                                          Integer userId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mPostRepository = itemModelRepository;
        mLimit = limit;
        mUserId = userId;
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
        final List<PostModel> posts = mPostRepository.getPostsByUserId(mLimit, mUserId);

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
