package curatetechnologies.com.curate.storage;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.repository.ItemModelRepository;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.converters.CurateItemConverter;
import curatetechnologies.com.curate.network.model.CurateAPIItem;
import curatetechnologies.com.curate.network.services.ItemService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemRepository implements ItemModelRepository {

    @Override
    public List<ItemModel> searchItems(String query) {
        final List<ItemModel> items = new ArrayList<>();

        // make network call
        Log.d("QUERY", query);
        Log.d("ItemService class", ItemService.class.toString());
        ItemService itemService = CurateClient.getService(ItemService.class);
        try {
            Response<List<CurateAPIItem>> response = itemService.searchItems(query).execute();
            Log.d("RESPONSE.BODY", "searchItems: " + response.body().toString());
            items.add(CurateItemConverter.convertCurateItemToItemModel(response.body().get(0)));
        } catch (IOException e){
            Log.d("FAILURE", e.getMessage());
        }
        return items;
    }
}
