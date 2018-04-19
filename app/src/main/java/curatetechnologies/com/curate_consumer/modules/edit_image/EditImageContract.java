package curatetechnologies.com.curate_consumer.modules.edit_image;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/10/18.
 */

public interface EditImageContract {

    interface View extends BaseView {
        void postSuccessful();
    }

    void postImagePost(PostModel postModel, String jwt);
}
