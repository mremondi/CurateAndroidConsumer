package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.network.model.CurateAPIItem;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemConverter {

    // Converts the outward facing data item model to the view model
    public static ItemModel convertCurateItemToItemModel(CurateAPIItem apiItem){
        ItemModel itemModel = new ItemModel(apiItem.getItemID(), apiItem.getItemName(), apiItem.getItemDescription(), apiItem.getItemImageURL());
        return itemModel;
    }

    // Converts the item view model to the outward facing data model
    public static CurateAPIItem convertItemModelToCurateItem(CurateAPIItem itemModel){
        // TODO: Do actual conversions
        CurateAPIItem apiItem = new CurateAPIItem();
        return apiItem;
    }
}
