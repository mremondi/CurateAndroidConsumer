package curatetechnologies.com.curate_consumer.modules.onboarding_workflow;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.TagTypeModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/15/18.
 */

public interface OnBoardUserContract {

    interface View extends BaseView {
        void saveUserPreferences();
        void segueToMainApp();
    }

    void saveUser(UserModel user, boolean isSocialLogin);
    void saveUserPreferences(UserModel user, List<TagTypeModel> preferences);
}
