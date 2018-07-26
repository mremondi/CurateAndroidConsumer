package curatetechnologies.com.curate_consumer.modules.restaurant;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/21/18.
 */

public interface RestaurantContract {

    interface View extends BaseView {
        void displayRestaurant(RestaurantModel restaurant);
        void displayRestaurantPosts(List<PostModel> posts);
        void displayOpenClosed(boolean isOpen);
    }

    void getRestaurantById(Integer restaurantId);

    void getRestaurantPosts(Integer limit, Integer restaurantId);

    void isRestaurantOpen(Integer restaurantId);
}
