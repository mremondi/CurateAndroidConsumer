package curatetechnologies.com.curate_consumer.modules.feed;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/22/18.
 */

public interface FeedContract {

    interface View extends BaseView {
        void displayPosts(List<PostModel> items);
    }

    void getPostsByLocation(Integer limit, Location location, Float radius);
}
