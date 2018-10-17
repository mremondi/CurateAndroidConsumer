package curatetechnologies.com.curate_consumer.network.Builders;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.SearchItemsQuery;
import curatetechnologies.com.curate_consumer.graphql.api.fragment.MenuInfo;

public class SearchItemsBuilder {

    public static List<ItemModel> buildItems(SearchItemsQuery.Data data) {
        List<ItemModel> itemModels = new ArrayList<>();
        List<SearchItemsQuery.Item> items = data.items();

        for (SearchItemsQuery.Item item : items) {
            ItemModel itemModel = buildItem(item);
            itemModels.add(itemModel);
        }

        return itemModels;
    }

    //TODO: Some of the info being built below is unnecessary. Look at the SearchItems view/presentr
    //TODO: and check if we don't need menus/menus sections -> set the arguments to null if so.

    private static ItemModel buildItem(SearchItemsQuery.Item item) {
        String imageURL;
        //Have to check if the posts array actually contain a post to grab an image from.
        if (item.posts().size() > 0) {
            imageURL = item.posts().get(0).imageUrl();
        } else {
            imageURL = null;
        }

        RestaurantModel restaurantModel = buildRestaurant(item.fragments().menuInfo().menu().restaurant());

        ItemModel itemModel = new ItemModel(item.id(), item.name(), item.description(), imageURL,
                String.format("%.2f", item.distance())+ "mi", "$" + String.format("%.2f", item.price()), item.price(), item.rating(),
                restaurantModel.getName(),null, null, restaurantModel.getId(),
                null, restaurantModel.getStripeID(), item.available(), null);

        return itemModel;
    }

    private static RestaurantModel buildRestaurant(MenuInfo.Restaurant restaurant){
        return new RestaurantModel(restaurant.id(), restaurant.name(), "", "",
                null, 0.0, "", "", null,
                "", restaurant.stripeId(), 0.0);
    }

}
