package curatetechnologies.com.curate.presentation.presenters;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/22/18.
 */

public interface FeedContract {

    interface View extends BaseView {
        void displayPosts(List<PostModel> items);
    }

    void getPostsByLocation(Integer limit, Location location, Float radius);
}
