package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.network.CurateAPI;
import curatetechnologies.com.curate_consumer.network.CurateAPIClient;

public class ItemRepository implements ItemModelRepository, CurateAPI.GetItemByIdCallback,
        CurateAPI.SearchItemsCallback {

    private ItemModelRepository.GetItemByIdCallback mGetItemByIdCallback;
    private ItemModelRepository.SearchItemsCallback mSearchItemsCallback;

    @Override
    public void searchItems(SearchItemsCallback callback, String query,
                                       Location location, Integer userId, Float radius) {

        mSearchItemsCallback = callback;
        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.searchItems(this, query, location, radius);
    }


    @Override
    public void getItemById(ItemModelRepository.GetItemByIdCallback callback, Integer itemId, Location location, Float radiusMiles) {
        mGetItemByIdCallback = callback;

        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.getItemById(this, itemId, location, Math.round(radiusMiles));
    }

    // BEGIN GetItemByIDCallback

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

    // END GetItemByIdCallback

    //BEGIN SEARCHITEMS CALLBACK

    @Override
    public void onItemsRetrieved(List<ItemModel> itemModels) {
        if (mSearchItemsCallback != null){
            mSearchItemsCallback.postSearchItems(itemModels);
        }
    }


    //END SEARCHITEMS CALLBACK

}
