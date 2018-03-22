package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 3/22/18.
 */

public interface MainActivityContract {

    interface View extends BaseView {
        void updateUser(UserModel userModel);
    }

    void getUserById(Integer userId);
}
