package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetPostsByUserIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetPostsByUserIdInteractorImpl;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.storage.PostModelRepository;

/**
 * Created by mremondi on 2/28/18.
 */

public class ProfilePresenter extends AbstractPresenter implements ProfileContract, GetPostsByUserIdInteractor.Callback {

    private ProfileContract.View mView;
    private PostModelRepository mPostRepository;

    public ProfilePresenter(Executor executor,
                         MainThread mainThread,
                         View view,
                         PostModelRepository postRepository){
        super(executor, mainThread);
        mView = view;
        mPostRepository = postRepository;
    }

    // -- BEGIN: FeedContract methods
    @Override
    public void getUserPosts(Integer limit, Integer userId) {
        mView.showProgress();
        GetPostsByUserIdInteractor getPostsInteractor = new GetPostsByUserIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPostRepository,
                limit,
                userId
        );
        getPostsInteractor.execute();
    }
    // -- END: FeedContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetPostsByUserIdInteractor.Callback methods
    @Override
    public void onPostsRetrieved(List<PostModel> posts) {
        mView.hideProgress();
        mView.displayPosts(posts);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetPostsByUserIdInteractor.Callback methods
}