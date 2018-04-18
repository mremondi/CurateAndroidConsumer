package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginContract {

    interface View extends BaseView {
        void segueToMainApp();
        void segueToOnboarding();
    }
    void getCurrentUser();
    void signUpWithFacebook(UserModel userModel);
    void signUpWithGoogle(UserModel userModel);
}