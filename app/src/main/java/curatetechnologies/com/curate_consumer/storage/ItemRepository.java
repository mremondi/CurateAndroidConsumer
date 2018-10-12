package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.network.CurateAPIClient;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.CurateAPI;
import curatetechnologies.com.curate_consumer.network.converters.curate.ItemConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIItem;
import curatetechnologies.com.curate_consumer.network.services.ItemService;
import retrofit2.Response;

public class ItemRepository implements ItemModelRepository, CurateAPI.GetItemByIdCallback {

    private ItemModelRepository.GetItemByIdCallback mGetItemByIdCallback;

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
    public void getItemById(ItemModelRepository.GetItemByIdCallback callback, Integer itemId, Location location, Float radiusMiles) {
        mGetItemByIdCallback = callback;
        ItemModel item = null;

        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.getItemById(this, itemId, location, Math.round(radiusMiles));
    }


    @Override
    public void onItemRetrieved(ItemModel itemModel){
        Log.d("ITEM RETRIEVED", itemModel.getName());
        Log.d("ITEM RETRIEVED", itemModel.getMenuName());

        if (mGetItemByIdCallback != null){
            mGetItemByIdCallback.postItem(itemModel);
        }
    }

    @Override
    public void onFailure(String message) {
        Log.d("ITEM RETRIEVAL FAILURE", message);
        if (mGetItemByIdCallback != null){
            mGetItemByIdCallback.notifyError(message);
        }
    }
}
