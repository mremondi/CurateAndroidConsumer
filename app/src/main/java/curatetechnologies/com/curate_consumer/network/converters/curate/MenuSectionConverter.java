package curatetechnologies.com.curate_consumer.network.converters.curate;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuSectionModel;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIItem;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIMenuSection;

/**
 * Created by mremondi on 2/23/18.
 */

public class MenuSectionConverter {

    public static MenuSectionModel convertCurateMenuSectionToMenuSectionModel(CurateAPIMenuSection apiSection){
        List<ItemModel> items = new ArrayList<>();
        for (CurateAPIItem apiItem: apiSection.getItems()){
            items.add(ItemConverter.convertCurateItemToItemModel(apiItem));
        }
        return new MenuSectionModel(apiSection.getMenuSectionID(), apiSection.getMenuSectionName(),
                items);
    }
}
