package curatetechnologies.com.curate_consumer.modules.profile;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetPostsByUserIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetPostsByUserIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

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
        if (mView.isActive()) {
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
    }
    // -- END: FeedContract methods

    // -- BEGIN: GetPostsByUserIdInteractor.Callback methods
    @Override
    public void onPostsRetrieved(List<PostModel> posts) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayPosts(posts);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: GetPostsByUserIdInteractor.Callback methods
}