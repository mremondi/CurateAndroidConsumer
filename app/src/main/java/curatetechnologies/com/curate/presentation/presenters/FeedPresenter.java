package curatetechnologies.com.curate.presentation.presenters;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetPostsByLocationInteractor;
import curatetechnologies.com.curate.domain.interactor.GetPostsByLocationInteractorImpl;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.storage.PostModelRepository;

/**
 * Created by mremondi on 2/22/18.
 */

public class FeedPresenter extends AbstractPresenter implements FeedContract, GetPostsByLocationInteractor.Callback {

    private FeedContract.View mView;
    private PostModelRepository mPostRepository;

    public FeedPresenter(Executor executor,
                         MainThread mainThread,
                         View view,
                         PostModelRepository postRepository){
        super(executor, mainThread);
        mView = view;
        mPostRepository = postRepository;
    }

    // -- BEGIN: FeedContract methods
    @Override
    public void getPostsByLocation(Integer limit, Location location, Float radius) {
        GetPostsByLocationInteractor getPostsInteractor = new GetPostsByLocationInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPostRepository,
                limit,
                location,
                radius
        );
        getPostsInteractor.execute();
    }
    // -- END: FeedContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetPostsByLocationInteractor.Callback methods
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
    // -- END: GetPostsByLocationInteractor.Callback methods
}