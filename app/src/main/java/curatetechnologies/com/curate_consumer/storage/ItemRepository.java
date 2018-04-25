package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.converters.curate.ItemConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIItem;
import curatetechnologies.com.curate_consumer.network.services.ItemService;
import retrofit2.Response;

public class ItemRepository implements ItemModelRepository {

    @Override
    public List<ItemModel> searchItems(String query, Location location, Integer userId, Float radius) {
        final List<ItemModel> items = new ArrayList<>();

        // make network call
        ItemService itemService = CurateClient.getService(ItemService.class);
        try {
            Response<List<CurateAPIItem>> response = itemService
                    .searchItems(query, userId, location.getLatitude(), location.getLongitude(), radius)
                    .execute();
            for (CurateAPIItem item: response.body()){
                items.add(ItemConverter.convertCurateItemToItemModel(item));
            }
        } catch (Exception e){
            Log.d("FAILURE searching", e.getLocalizedMessage());
        }
        return items;
    }

    @Override
    public ItemModel getItemById(Integer itemId, Location location, Float radiusMiles) {
        ItemModel item = null;
        ItemService itemService = CurateClient.getService(ItemService.class);
        try {
            Response<List<CurateAPIItem>> response = itemService
                    .getItemById(itemId, location.getLatitude(), location.getLongitude(), radiusMiles)
                    .execute();
            item = ItemConverter.convertCurateItemToItemModel(response.body().get(0));
        } catch (Exception e){
            Log.d("FAILURE1", e.getMessage());
        }
        return item;
    }
}
