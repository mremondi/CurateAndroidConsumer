package curatetechnologies.com.curate_consumer.modules.onboarding_workflow;

import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/18/18.
 */

public interface OnBoardingFragment1Contract {

    interface View extends BaseView {
        void usernameAvailable(Boolean available);
    }

    void checkUsernameAvailable(String username);
}
