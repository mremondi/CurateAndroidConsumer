package curatetechnologies.com.curate_consumer.modules.profile;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/28/18.
 */

public interface ProfileContract {

    interface View extends BaseView {
        void displayPosts(List<PostModel> posts);
    }

    void getUserPosts(Integer limit, Integer userId);
}
