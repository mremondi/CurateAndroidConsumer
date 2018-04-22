package curatetechnologies.com.curate_consumer.storage;

import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.converters.curate.MenuConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIMenu;
import curatetechnologies.com.curate_consumer.network.services.MenuService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/23/18.
 */

public class MenuRepository implements MenuModelRepository{

    @Override
    public MenuModel getMenuById(Integer menuId) {
        MenuModel menu = null;

        // make network call
        MenuService menuService = CurateClient.getService(MenuService.class);
        try {
            Response<List<CurateAPIMenu>> response = menuService.getMenuById(menuId).execute();
            menu = MenuConverter.convertCurateMenuToMenuModel(response.body().get(0));
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return menu;
    }
}