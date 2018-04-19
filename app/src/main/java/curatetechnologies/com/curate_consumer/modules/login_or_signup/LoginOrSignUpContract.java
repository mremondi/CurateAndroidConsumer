package curatetechnologies.com.curate_consumer.modules.login_or_signup;

import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginOrSignUpContract {

    interface View extends BaseView {
        void segueToMainApp();
    }
    void getCurrentUser();
}