package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.ui.BaseView;

/**
 * Created by mremondi on 2/9/18.
 */

public interface ItemSearchPresenter {

    interface View extends BaseView {
        void displayItems(List<ItemModel> items);
    }

    void searchItems(String query);
}
