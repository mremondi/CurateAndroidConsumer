package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import java.util.Locale;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.network.model.CurateAPIItem;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemConverter {

    // Converts the outward facing data item model to the view model
    public static ItemModel convertCurateItemToItemModel(CurateAPIItem apiItem){
        Double ratingDouble = apiItem.getItemSumOfOverallRatings().doubleValue() /apiItem.getItemNumberOfOverallRatings();
        String rating = (ratingDouble.isNaN()) ? "" :  String.format("%.2f", ratingDouble);
        ItemModel itemModel = new ItemModel(apiItem.getItemID(), apiItem.getItemName(), apiItem.getItemDescription(),
                apiItem.getItemImageURL(), String.format("%.2f", apiItem.getDistanceInMiles()) + "mi",
                "$" + String.format(Locale.US, "%.2f", apiItem.getItemPrice()),
               rating, apiItem.getRestaurantName(), apiItem.getMenuName(), apiItem.getMenuSectionName(),
                apiItem.getRestaurantID(), apiItem.getMenuID());
        return itemModel;
    }

    // Converts the item view model to the outward facing data model
    public static CurateAPIItem convertItemModelToCurateItem(CurateAPIItem itemModel){
        // TODO: Do actual conversions
        CurateAPIItem apiItem = new CurateAPIItem();
        return apiItem;
    }
}
