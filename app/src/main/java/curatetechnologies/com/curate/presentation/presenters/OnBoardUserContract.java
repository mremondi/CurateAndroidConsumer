package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/15/18.
 */

public interface OnBoardUserContract {

    interface View extends BaseView {
        void beginOnBoarding(UserModel user);
        void segueToMainApp();
    }

    void getCurrentUser();
    void saveUser(UserModel user);
    void saveUserPreferences(List<TagTypeModel> preferences);
}
