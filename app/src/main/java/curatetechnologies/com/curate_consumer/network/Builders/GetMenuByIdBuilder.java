package curatetechnologies.com.curate_consumer.network.Builders;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuSectionModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetMenuByIdQuery;

public class GetMenuByIdBuilder {

    public static MenuModel buildMenu(GetMenuByIdQuery.Data data) {
        MenuModel menuModel = null;
        List<MenuSectionModel> menuSectionsModel;

        GetMenuByIdQuery.Menus menus = data.menus().get(0);

        menuSectionsModel = buildMenuSections(menus.menuSections());
        menuModel = new MenuModel(menus.id(), menus.name(), menus.restaurantId(), menuSectionsModel);

        return menuModel;
    }

    private static List<MenuSectionModel> buildMenuSections(List<GetMenuByIdQuery.MenuSection> menuSections) {
        List<MenuSectionModel> menuSectionModels = new ArrayList<>();

        for (GetMenuByIdQuery.MenuSection menuSection : menuSections) {
            menuSectionModels.add(new MenuSectionModel(menuSection.id(), menuSection.section(), buildItems(menuSection)));
        }

        return menuSectionModels;
    }

    private static List<ItemModel> buildItems(GetMenuByIdQuery.MenuSection menuSection) {
        List<ItemModel> itemsModel = new ArrayList<>();


        for (GetMenuByIdQuery.Item item : menuSection.items()) {

            double price = item.price();
            Log.d("GetMenuBuilder", "Item price is: " + String.format("%.2f", item.price()));

            String imageURL;

            //Have to check if the posts array actually contain a post to grab an image from.
            if (item.posts().size() > 0) {
                imageURL = item.posts().get(0).imageUrl();
            } else {
                imageURL = null;
            }

            itemsModel.add(new ItemModel(item.id(), item.name(), item.description(), imageURL,
                    null, "$" + String.format("%.2f", item.price()), null, item.rating(),
                    null, null, null, null,
                    null, null, true, null));
        }

        return itemsModel;
    }

}
