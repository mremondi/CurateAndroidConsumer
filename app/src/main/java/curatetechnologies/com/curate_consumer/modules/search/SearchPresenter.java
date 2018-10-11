package curatetechnologies.com.curate_consumer.modules.search;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.SearchItemsInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SearchItemsInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SearchRestaurantsInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SearchRestaurantsInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.ItemModelRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

/**
 * Created by mremondi on 2/9/18.
 */

public class SearchPresenter extends AbstractPresenter implements SearchContract,
        SearchItemsInteractor.Callback, SearchRestaurantsInteractor.Callback {

    private SearchContract.View mView;
    private ItemModelRepository mItemRepository;
    private RestaurantModelRepository mRestaurantRepository;

    public SearchPresenter(Executor executor, MainThread mainThread,
                           View view, ItemModelRepository itemRepository,
                            RestaurantModelRepository restaurantRepository) {
        super(executor, mainThread);
        mView = view;
        mItemRepository = itemRepository;
        mRestaurantRepository = restaurantRepository;
    }

    // -- BEGIN: SearchContract methods
    @Override
    public void searchItems(String query, Location location, Integer userId, Float radius) {
        if (mView.isActive()) {
            mView.showProgress();
            SearchItemsInteractor searchItemsInteractor = new SearchItemsInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mItemRepository,
                    query,
                    location,
                    userId,
                    radius
            );
            searchItemsInteractor.execute();
        }
    }

    @Override
    public void searchRestaurants(String query, Location location, Integer userId, Float radius) {
        if (mView.isActive()) {
            mView.showProgress();
            SearchRestaurantsInteractorImpl searchRestaurantsInteractor = new SearchRestaurantsInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mRestaurantRepository,
                    query,
                    location,
                    userId,
                    radius
            );
            searchRestaurantsInteractor.execute();
        }
    }
    // -- END: SearchContract methods

    // -- BEGIN: SearchItemsInteractor.Callback methods
    @Override
    public void onSearchItemsRetrieved(List<ItemModel> items) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayItems(items);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: SearchItemsInteractor.Callback methods

    // -- BEGIN: SearchRestaurantsInteractor.Callback methods
    @Override
    public void onSearchRestaurantsRetrieved(List<RestaurantModel> restaurants) {
        mView.hideProgress();
        mView.displayRestaurants(restaurants);
    }
    // NOTE: onRetrievalFailed implemented above^^
    // -- END: SearchRestaurantsInteractor.Callback methods
}
