package curatetechnologies.com.curate_consumer.network.converters.curate;

import java.util.Locale;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.network.firebase.model.FirebaseItem;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIItem;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemConverter {

    // Converts the outward facing data item model to the view model
    public static ItemModel convertCurateItemToItemModel(CurateAPIItem apiItem){
        Double rating = 0.0;
        if (apiItem.getItemSumOfOverallRatings() != null && apiItem.getItemNumberOfOverallRatings() != null
                && apiItem.getItemNumberOfOverallRatings() != 0) {
            rating = apiItem.getItemSumOfOverallRatings().doubleValue() / apiItem.getItemNumberOfOverallRatings();
        }
        String stripeID;
        if (apiItem.getRestaurantStripeID().equals("undefined")) {
            stripeID = null;
        } else {
            stripeID = apiItem.getRestaurantStripeID();
        }

        ItemModel itemModel = new ItemModel(apiItem.getItemID(), apiItem.getItemName(), apiItem.getItemDescription(),
                apiItem.getItemImageURL(), String.format("%.2f", apiItem.getDistanceInMiles()) + "mi",
                "$" + String.format(Locale.US, "%.2f", apiItem.getItemPrice()), apiItem.getItemPrice(),
               rating, apiItem.getRestaurantName(), apiItem.getMenuName(), apiItem.getMenuSectionName(),
                apiItem.getRestaurantID(), apiItem.getMenuID(), stripeID, apiItem.getItemAvailable());
        return itemModel;
    }

    // Converts the item view model to the outward facing data model
    public static CurateAPIItem convertItemModelToCurateItem(CurateAPIItem itemModel){
        // TODO: Do actual conversions
        CurateAPIItem apiItem = new CurateAPIItem();
        return apiItem;
    }


    public static FirebaseItem convertItemModelToFirebaseItem(ItemModel itemModel){
        return new FirebaseItem(String.valueOf(itemModel.getId()),
                itemModel.getName(), itemModel.getPrice());
    }
}
