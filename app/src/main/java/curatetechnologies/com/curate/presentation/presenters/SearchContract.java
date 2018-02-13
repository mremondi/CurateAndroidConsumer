package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface SearchContract {

    interface View extends BaseView {
        void displayItems(List<ItemModel> items);
        void displayRestaurants(List<RestaurantModel> restaurants);
    }

    void searchItems(String query);
    void searchRestaurants(String query);
}