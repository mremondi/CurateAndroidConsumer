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
        void saveUserPreferences();
        void segueToMainApp();
    }

    void saveUser(UserModel user);
    void saveUserPreferences(UserModel user, List<TagTypeModel> preferences);
}
