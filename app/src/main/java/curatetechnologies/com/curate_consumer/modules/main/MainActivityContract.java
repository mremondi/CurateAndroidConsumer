package curatetechnologies.com.curate_consumer.modules.main;

import android.content.Context;

import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.ui.BaseView;

/**
 * Created by mremondi on 3/22/18.
 */

public interface MainActivityContract {

    interface View extends BaseView {
        void updateUser(UserModel userModel);
        void rateLastOrder(OrderModel orderModel);
    }

    void getUserById(Integer userId);
    void getLastOrder(Context appContext);
}
