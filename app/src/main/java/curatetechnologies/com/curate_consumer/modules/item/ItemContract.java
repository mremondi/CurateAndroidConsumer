package curatetechnologies.com.curate_consumer.modules.item;

/**
 * Created by mremondi on 2/10/18.
 */

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ItemContract {

    interface View extends BaseView {
        void displayItem(ItemModel item);
        void postCreatedSuccessfully();

    }

    void getItemById(Integer itemId, Location location, Float radiusMiles);
    void createRatingPost(String jwt, PostModel postModel);
}