package curatetechnologies.com.curate_consumer.modules.map;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetNearbyRestaurantsInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetNearbyRestaurantsInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

public class MapPresenter extends AbstractPresenter implements MapContract,
        GetNearbyRestaurantsInteractor.Callback {

    private MapContract.View mView;
    private RestaurantModelRepository mRestaurantRepository;

    public MapPresenter(Executor executor, MainThread mainThread,
                        MapContract.View view, RestaurantModelRepository restaurantRepository) {
        super(executor, mainThread);
        mView = view;
        mRestaurantRepository = restaurantRepository;
    }
    @Override
    public void getNearbyRestaurants(Location location, Integer userId, Float radius) {
        if (mView.isActive()) {
            mView.showProgress();
            GetNearbyRestaurantsInteractor nearbyRestaurantsInteractor = new GetNearbyRestaurantsInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mRestaurantRepository,
                    location,
                    userId,
                    radius
            );
            nearbyRestaurantsInteractor.execute();
        }
    }

    @Override
    public void onRestaurantsRetrieved(List<RestaurantModel> restaurants) {
        if (mView.isActive()) {
            mView.displayRestaurants(restaurants);
            mView.hideProgress();
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.showError(error);
            mView.hideProgress();
        }
    }
}
