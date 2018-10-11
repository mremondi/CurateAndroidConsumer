package curatetechnologies.com.curate_consumer.modules.restaurant;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantPostsInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantPostsInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.IsRestaurantOpenInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.IsRestaurantOpenInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;


public class RestaurantPresenter extends AbstractPresenter implements
        RestaurantContract,
        GetRestaurantByIdInteractor.Callback,
        GetRestaurantPostsInteractor.Callback,
        IsRestaurantOpenInteractor.Callback {

    private RestaurantContract.View mView;
    private RestaurantModelRepository mRestaurantRepository;
    private PostModelRepository mPostsRepository;

    public RestaurantPresenter(Executor executor, MainThread mainThread,
                               View view, RestaurantModelRepository restaurantRepository,
                               PostModelRepository postRepository) {
        super(executor, mainThread);
        mView = view;
        mRestaurantRepository = restaurantRepository;
        mPostsRepository = postRepository;
    }

    // -- BEGIN: RestaurantContract methods
    @Override
    public void getRestaurantById(Integer restaurantId) {
        if (mView.isActive()) {
            mView.showProgress();
            GetRestaurantByIdInteractor restaurantInteractor = new GetRestaurantByIdInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mRestaurantRepository,
                    restaurantId
            );
            restaurantInteractor.execute();
        }
    }

    @Override
    public void getRestaurantPosts(Integer limit, Integer restaurantId) {
        GetRestaurantPostsInteractor restaurantPostsInteractor = new GetRestaurantPostsInteractorImpl(
            mExecutor,
            mMainThread,
            this,
            mPostsRepository,
            limit,
            restaurantId
        );
        restaurantPostsInteractor.execute();
    }

    @Override
    public void isRestaurantOpen(Integer restaurantId) {
        IsRestaurantOpenInteractor isRestaurantOpenInteractor = new IsRestaurantOpenInteractorImpl(
          mExecutor,
          mMainThread,
          this,
          mRestaurantRepository,
          restaurantId
        );
        isRestaurantOpenInteractor.execute();
    }

    // -- END: RestaurantContract methods


    // -- BEGIN: GetRestaurantByIdInteractor.Callback methods
    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurant) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayRestaurant(restaurant);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: GetRestaurantByIdInteractor.Callback methods


    @Override
    public void onPostsRetrieved(List<PostModel> posts) {
        if (mView.isActive()) {
            mView.displayRestaurantPosts(posts);
        }
    }

    @Override
    public void onOpenClosedRetrieved(boolean isOpen) {
        if (mView.isActive()) {
            mView.displayOpenClosed(isOpen);
        }
    }

    @Override
    public void onRetrieveOpenClosedFailed(String error) {
        if (mView.isActive()) {
            mView.showError(error);
        }
    }
}