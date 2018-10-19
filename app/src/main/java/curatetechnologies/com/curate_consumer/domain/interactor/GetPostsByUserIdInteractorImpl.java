package curatetechnologies.com.curate_consumer.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

/**
 * Created by mremondi on 2/28/18.
 */

public class GetPostsByUserIdInteractorImpl extends AbstractInteractor implements
        GetPostsByUserIdInteractor, PostModelRepository.GetPostsByUserIdCallback {

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

    public void notifyError(String message) {
        mMainThread.post( () -> {
            mCallback.onRetrievalFailed(message);
        });
    }

    public void postUserPosts(List<PostModel> postModels) {
        mMainThread.post( ()-> {
            mCallback.onPostsRetrieved(postModels);
        });
    }


    @Override
    public void run() {    mPostRepository.getPostsByUserId(this, mLimit, mUserId); }
}
