package curatetechnologies.com.curate_consumer.network;

import android.location.Location;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;

public interface CurateAPI {

    void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId, Location location, int radius);
    void getMenuById(final CurateAPI.GetMenuByIdCallback menuModelRepository, int menuId);

    interface GetItemByIdCallback {
        void onItemRetrieved(ItemModel itemModel);
        void onFailure(String message);
    }

    interface GetMenuByIdCallback {
        void onMenuRetrieved(MenuModel menuModel);
        void onFailure(String message);
    }
}
