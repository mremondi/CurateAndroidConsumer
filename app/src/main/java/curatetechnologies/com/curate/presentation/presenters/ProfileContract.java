package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/28/18.
 */

public interface ProfileContract {

    interface View extends BaseView {
        void displayPosts(List<PostModel> posts);
    }

    void getUserPosts(Integer limit, Integer userId);
}
