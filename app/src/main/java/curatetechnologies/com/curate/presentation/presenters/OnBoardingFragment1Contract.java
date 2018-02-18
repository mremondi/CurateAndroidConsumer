package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/18/18.
 */

public interface OnBoardingFragment1Contract {

    interface View extends BaseView {
        void usernameAvailable(Boolean available);
    }

    void checkUsernameAvailable(String username);
}
