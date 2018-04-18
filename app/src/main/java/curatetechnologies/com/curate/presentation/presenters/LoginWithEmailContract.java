package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginWithEmailContract {
    interface View extends BaseView {
        void updateUI();
        void saveUser(UserModel user);
    }

    void loginUserWithEmail(String email, String password);
    void saveUser(UserModel user);
}
