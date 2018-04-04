package curatetechnologies.com.curate.presentation.presenters;

/**
 * Created by mremondi on 2/10/18.
 */

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ItemContract {

    interface View extends BaseView {
        void displayItem(ItemModel item);
        void postCreatedSuccessfully();

    }

    void getItemById(Integer itemId, Location location);
    void createRatingPost(String jwt, PostModel postModel);
}