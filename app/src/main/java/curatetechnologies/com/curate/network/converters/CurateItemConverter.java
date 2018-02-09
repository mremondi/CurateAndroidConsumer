package curatetechnologies.com.curate.network.converters;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.network.model.CurateAPIItem;

/**
 * Created by mremondi on 2/9/18.
 */

public class CurateItemConverter {


    public static ItemModel convertCurateItemToItemModel(CurateAPIItem apiItem){
        // TODO: Do actual conversions
        ItemModel itemModel = new ItemModel();
        return itemModel;
    }

    public static CurateAPIItem convertItemModelToCurateItem(CurateAPIItem itemModel){
        // TODO: Do actual conversions
        CurateAPIItem apiItem = new CurateAPIItem();
        return apiItem;
    }
}
