package curatetechnologies.com.curate_consumer.modules.feed;

import android.location.Location;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetPostsByLocationInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetPostsByLocationInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

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
        if (mView.isActive()) {
            mView.showProgress();
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
    }
    // -- END: FeedContract methods

    public void onError(String message) {
        if (mView.isActive()) {
            mView.showError(message);
        }
    }

    // -- BEGIN: GetPostsByLocationInteractor.Callback methods
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
            onError(error);
        }
    }
    // -- END: GetPostsByLocationInteractor.Callback methods
}