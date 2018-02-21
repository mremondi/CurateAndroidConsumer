package curatetechnologies.com.curate.presentation.presenters;

import android.util.Log;
import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.storage.RestaurantModelRepository;


public class RestaurantPresenter extends AbstractPresenter implements RestaurantContract, GetRestaurantByIdInteractor.Callback {

    private RestaurantContract.View mView;
    private RestaurantModelRepository mRestaurantRepository;

    public RestaurantPresenter(Executor executor, MainThread mainThread,
                         View view, RestaurantModelRepository restaurantRepository) {
        super(executor, mainThread);
        mView = view;
        mRestaurantRepository = restaurantRepository;
    }

    // -- BEGIN: RestaurantContract methods
    @Override
    public void getRestaurantById(Integer restaurantId) {
        Log.d("getItemById", "presenter");
        GetRestaurantByIdInteractor restaurantInteractor = new GetRestaurantByIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mRestaurantRepository,
                restaurantId
        );
        restaurantInteractor.execute();
    }
    // -- END: RestaurantContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetRestaurantByIdInteractor.Callback methods
    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurant) {
        Log.d("get item by id", "presenter callback");
        mView.hideProgress();
        mView.displayRestaurant(restaurant);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetRestaurantByIdInteractor.Callback methods
}