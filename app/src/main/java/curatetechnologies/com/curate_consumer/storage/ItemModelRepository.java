package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;

/**
 * Created by mremondi on 2/9/18.
 */

public interface ItemModelRepository {

    List<ItemModel> searchItems(SearchItemsCallback callback, String query,
                                Location location, Integer userId, Float radius);
    void getItemById(GetItemByIdCallback callback, Integer itemId, Location location, Float radiusMiles);

    interface GetItemByIdCallback{
        void postItem(final ItemModel item);
        void notifyError(String message);
    }

    interface SearchItemsCallback {
        void postSearchItems(final List<ItemModel> items);
        void notifyError(String message);
    }
}
