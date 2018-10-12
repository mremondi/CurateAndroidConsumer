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
        if (apiItem.getRestaurantStripeID() == null) {
            stripeID = null;
        } else if (apiItem.getRestaurantStripeID().equals("undefined") || apiItem.getRestaurantStripeID().equals("")) {
            stripeID = null;
        } else {
            stripeID = apiItem.getRestaurantStripeID();
        }

        String itemName = (apiItem.getItemName() != null) ? apiItem.getItemName() : "";
        String itemDescription = (apiItem.getItemDescription() != null) ? apiItem.getItemDescription() : "";
        String itemImageUrl = (apiItem.getItemImageURL() != null) ? apiItem.getItemImageURL() : "";
        String distance = (apiItem.getDistanceInMiles() != null) ? String.format("%.2f", apiItem.getDistanceInMiles())+ "mi" : "";
        Double numericPrice = (apiItem.getItemPrice() != null) ? apiItem.getItemPrice() : 0.0;
        String price = (apiItem.getItemPrice() != null) ? "$" + String.format(Locale.US, "%.2f", apiItem.getItemPrice()) : "";
        String restaurantName = (apiItem.getRestaurantName() != null) ? apiItem.getRestaurantName() : "";
        String menuName = (apiItem.getMenuName() != null) ? apiItem.getMenuName() : "";
        String menuSectionName = (apiItem.getMenuSectionName() != null) ? apiItem.getMenuSectionName() : "";
        Integer restaurantId = (apiItem.getRestaurantID() != null) ? apiItem.getRestaurantID() : -1;
        Integer menuId = (apiItem.getMenuID() != null) ? apiItem.getMenuID() : -1;
        Boolean available = (apiItem.getItemAvailable() != null) ? apiItem.getItemAvailable() : false;

        ItemModel itemModel = new ItemModel(apiItem.getItemID(), itemName, itemDescription,
                itemImageUrl,  distance, price, numericPrice, rating, restaurantName, menuName,
                menuSectionName, restaurantId, menuId, stripeID, available, null);
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
