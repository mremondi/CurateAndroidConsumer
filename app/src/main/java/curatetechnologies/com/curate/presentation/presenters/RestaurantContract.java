package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/21/18.
 */

public interface RestaurantContract {

    interface View extends BaseView {
        void displayRestaurant(RestaurantModel restaurant);
        void displayRestaurantPosts(List<PostModel> posts);
    }

    void getRestaurantById(Integer restaurantId);

    void getRestaurantPosts(Integer limit, Integer restaurantId);
}
