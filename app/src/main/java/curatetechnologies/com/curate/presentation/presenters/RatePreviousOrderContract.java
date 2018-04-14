package curatetechnologies.com.curate.presentation.presenters;

import android.content.Context;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/12/18.
 */

public interface RatePreviousOrderContract {

    interface View extends BaseView {
        void postCreatedSuccessfully();

    }
    void createRatingPost(String jwt, PostModel postModel, Context context);
}