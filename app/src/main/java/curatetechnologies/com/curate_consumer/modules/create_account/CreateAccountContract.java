package curatetechnologies.com.curate_consumer.modules.create_account;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/19/18.
 */

public interface CreateAccountContract {

    interface View extends BaseView {
        void segueToOnboarding();
        void segueToMain();
    }
    void signUpWithFacebook(UserModel userModel);
    void signUpWithGoogle(UserModel userModel);
}