package curatetechnologies.com.curate_consumer.presentation.presenters;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/13/18.
 */

public interface CreateAccountWithEmailContract {
    interface View extends BaseView {
        void updateUI();
        void saveUser(UserModel user);
    }

    void createAccountEmailPassword(String email, String password);
    void saveUser(UserModel user);
}
