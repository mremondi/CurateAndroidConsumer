package curatetechnologies.com.curate_consumer.storage;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.network.CurateAPI;
import curatetechnologies.com.curate_consumer.network.CurateAPIClient;

/**
 * Created by mremondi on 2/23/18.
 */

public class MenuRepository implements MenuModelRepository, CurateAPI.GetMenuByIdCallback {

    private MenuModelRepository.GetMenuByIdCallback mGetMenuByIdCallback;

    @Override
    public void getMenuById(MenuModelRepository.GetMenuByIdCallback callback, Integer menuID) {
        mGetMenuByIdCallback = callback;
        MenuModel menuModel = null;

        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.getMenuById(this, menuID);
    }

    @Override
    public void onFailure(String message) {
        Log.d("ITEM RETRIEVAL FAILURE", message);
        if (mGetMenuByIdCallback != null){
            mGetMenuByIdCallback.notifyError(message);
        }
    }

    @Override
    public void onMenuRetrieved(MenuModel menuModel) {
        Log.d("MENU RETRIEVED", menuModel.getName());

        if (mGetMenuByIdCallback != null){
            mGetMenuByIdCallback.postMenu(menuModel);
        }
    }
}
