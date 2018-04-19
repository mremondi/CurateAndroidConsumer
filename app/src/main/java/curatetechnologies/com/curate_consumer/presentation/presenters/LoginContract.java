package curatetechnologies.com.curate_consumer.presentation.presenters;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

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