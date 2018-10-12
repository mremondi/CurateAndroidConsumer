package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;

/**
 * Created by mremondi on 2/9/18.
 */

public interface ItemModelRepository {

    List<ItemModel> searchItems(String query, Location location, Integer userId, Float radius);
    ItemModel getItemById(Integer itemId, Location location, Float radiusMiles);

    interface GetItemByIdCallback{
        void postItem(final ItemModel item);
        void notifyError(String message);
    }
}
