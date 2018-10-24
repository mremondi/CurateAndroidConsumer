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

        CurateAPI apiClient = new CurateAPIClient();
        mSearchItemsCallback = callback;
        apiClient.searchItems(this, query, (float)location.getLatitude(),
                (float)location.getLongitude(), Math.round(radius));
    }


    @Override
    public void getItemById(ItemModelRepository.GetItemByIdCallback callback, Integer itemId,
                            Location location, Float radiusMiles) {

        CurateAPI apiClient = new CurateAPIClient();
        mGetItemByIdCallback = callback;
        apiClient.getItemById(this, itemId, (float)location.getLatitude(),
                (float)location.getLongitude(), Math.round(radiusMiles));
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
    public void onGetItemByIdFailure(String message) {
        Log.d("ITEM RETRIEVAL FAILURE", message);
        if (mGetItemByIdCallback != null){
            mGetItemByIdCallback.notifyError(message);
        }
    }

    //END GetItemByIdCallback

    //BEGIN SEARCHITEMS CALLBACK

    @Override
    public void onItemsRetrieved(List<ItemModel> itemModels) {
        if (mSearchItemsCallback != null){
            mSearchItemsCallback.postSearchItems(itemModels);
        }
    }

    @Override
    public void onSearchItemsFailure(String message) {
        if (mSearchItemsCallback != null){
            mSearchItemsCallback.notifyError(message);
        }
    }

    public int getUniqueId() {
        return 32;
    }

    //END SEARCHITEMS CALLBACK

}
