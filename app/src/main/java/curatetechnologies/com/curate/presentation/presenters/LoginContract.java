package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/19/18.
 */

public interface LoginContract {

    interface View extends BaseView {
        void updateUI();
    }
    void saveUser(UserModel user);
}