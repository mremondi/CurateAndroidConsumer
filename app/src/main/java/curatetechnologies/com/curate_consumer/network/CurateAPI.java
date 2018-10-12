package curatetechnologies.com.curate_consumer.network;

import android.location.Location;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;

public interface CurateAPI {

    void getItemById(final CurateAPI.GetItemByIdCallback itemModelRepository, int itemId, Location location, int radius);

    interface GetItemByIdCallback {
        void onItemRetrieved(ItemModel itemModel);
        void onFailure(String message);
    }
}
