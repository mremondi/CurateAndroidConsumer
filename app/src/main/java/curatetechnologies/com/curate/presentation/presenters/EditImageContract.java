package curatetechnologies.com.curate.presentation.presenters;

import android.location.Location;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/10/18.
 */

public interface EditImageContract {

    interface View extends BaseView {
        void postSuccessful();
    }

    void postImagePost(PostModel postModel, String jwt);
}
