package curatetechnologies.com.curate_consumer.modules.rate_previous_order;

import android.content.Context;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.ClearLastOrderInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.ClearLastOrderInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.OrderModelRepository;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

/**
 * Created by mremondi on 4/12/18.
 */

public class RatePreviousOrderPresenter extends AbstractPresenter implements RatePreviousOrderContract,
        CreatePostInteractor.Callback,
        ClearLastOrderInteractor.Callback {

    private RatePreviousOrderContract.View mView;
    private PostModelRepository mPostRepository;
    private OrderModelRepository mOrderRepository;

    public RatePreviousOrderPresenter(Executor executor,
                                      MainThread mainThread,
                                      View view,
                                      PostModelRepository postModelRepository,
                                      OrderModelRepository orderModelRepository) {
        super(executor, mainThread);
        mView = view;
        mPostRepository = postModelRepository;
        mOrderRepository = orderModelRepository;
    }

    @Override
    public void createRatingPost(String jwt, PostModel postModel, Context context) {
        mView.showProgress();
        CreatePostInteractor createPostInteractor = new CreatePostInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPostRepository,
                jwt,
                postModel
        );
        createPostInteractor.execute();

        ClearLastOrderInteractor clearLastOrderInteractor = new ClearLastOrderInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mOrderRepository,
                context
        );
        clearLastOrderInteractor.execute();
    }


    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: CreatePostInteractor.Callback methods
    @Override
    public void onCreatePostSuccess() {
        mView.hideProgress();
        mView.postCreatedSuccessfully();
    }

    @Override
    public void onCreatePostFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: CreatePostInteractor.Callback methods


    @Override
    public void onOrderCleared() {
       // don't do anything
    }
}