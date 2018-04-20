package curatetechnologies.com.curate_consumer.modules.receipt;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

/**
 * Created by mremondi on 4/20/18.
 */

public class ReceiptPresenter extends AbstractPresenter implements ReceiptContract,
        GetRestaurantByIdInteractor.Callback {

    private ReceiptContract.View mView;
    private RestaurantModelRepository mRestaurantRepository;

    public ReceiptPresenter(Executor executor, MainThread mainThread,
                         View view, RestaurantModelRepository restaurantModelRepository) {
        super(executor, mainThread);
        mView = view;
        mRestaurantRepository = restaurantModelRepository;
    }

    // -- BEGIN: ReceiptContract methods
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
    // -- END: ReceiptContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetItemByIdInteractor.Callback methods
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
    // -- END: GetItemByIdInteractor.Callback methods
}