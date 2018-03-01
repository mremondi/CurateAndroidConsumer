package curatetechnologies.com.curate.presentation.presenters;

import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantPostsInteractor;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantPostsInteractorImpl;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.storage.PostModelRepository;
import curatetechnologies.com.curate.storage.RestaurantModelRepository;


public class RestaurantPresenter extends AbstractPresenter implements RestaurantContract,
        GetRestaurantByIdInteractor.Callback, GetRestaurantPostsInteractor.Callback {

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

    // -- END: RestaurantContract methods



    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetRestaurantByIdInteractor.Callback methods
    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurant) {
        mView.hideProgress();
        mView.displayRestaurant(restaurant);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetRestaurantByIdInteractor.Callback methods


    @Override
    public void onPostsRetrieved(List<PostModel> posts) {
        mView.displayRestaurantPosts(posts);
    }
}