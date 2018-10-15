package curatetechnologies.com.curate_consumer.storage;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;

/**
 * Created by mremondi on 2/23/18.
 */

public interface MenuModelRepository {

    void getMenuById(GetMenuByIdCallback callback, Integer menuId);

    interface GetMenuByIdCallback{
        void postMenu(final MenuModel menu);
        void notifyError(String message);
    }
}
