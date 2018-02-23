package curatetechnologies.com.curate.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.converters.curate.ItemConverter;
import curatetechnologies.com.curate.network.model.CurateAPIItem;
import curatetechnologies.com.curate.network.services.ItemService;
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
            Log.d("FAILURE", e.getMessage());
        }
        return items;
    }

    @Override
    public ItemModel getItemById(Integer itemId, Location location) {
        ItemModel item = null;
        ItemService itemService = CurateClient.getService(ItemService.class);
        try {
            Response<List<CurateAPIItem>> response = itemService
                    .getItemById(itemId, location.getLatitude(), location.getLongitude())
                    .execute();
            item = ItemConverter.convertCurateItemToItemModel(response.body().get(0));
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return item;
    }
}
