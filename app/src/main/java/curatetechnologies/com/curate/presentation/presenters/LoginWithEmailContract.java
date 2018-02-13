package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/13/18.
 */

public interface LoginWithEmailContract {
    interface View extends BaseView {
        void updateUI(UserModel user);
    }

    void loginUserEmailPassword(String email, String password);
}
