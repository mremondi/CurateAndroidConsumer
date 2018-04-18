package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 4/15/18.
 */

public interface LoginOrSignUpContract {

    interface View extends BaseView {
        void segueToMainApp();
    }
    void getCurrentUser();
}