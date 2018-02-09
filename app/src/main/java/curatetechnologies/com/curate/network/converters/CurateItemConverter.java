package curatetechnologies.com.curate.network.converters;

import android.util.Log;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.network.model.CurateAPIItem;

/**
 * Created by mremondi on 2/9/18.
 */

public class CurateItemConverter {


    public static ItemModel convertCurateItemToItemModel(CurateAPIItem apiItem){

        ItemModel itemModel = new ItemModel(apiItem.getItemName(), apiItem.getItemDescription(), apiItem.getItemImageURL());
        return itemModel;
    }

    public static CurateAPIItem convertItemModelToCurateItem(CurateAPIItem itemModel){
        // TODO: Do actual conversions
        CurateAPIItem apiItem = new CurateAPIItem();
        return apiItem;
    }
}
