package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.presenters.ItemContract;
import curatetechnologies.com.curate.presentation.presenters.ItemPresenter;
import curatetechnologies.com.curate.storage.ItemRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;


public class ItemFragment extends Fragment implements ItemContract.View {

    public static final String ITEM_ID = "itemId";

    private ItemPresenter mItemPresenter;

    Unbinder unbinder;

    @BindView(R.id.fragment_item_item_name)
    TextView tvItemName;

    // -- BEGIN Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        unbinder = ButterKnife.bind(this, v);

        Integer itemId = getArguments().getInt(ITEM_ID);

        mItemPresenter = new ItemPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new ItemRepository());

        mItemPresenter.getItemById(itemId);
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    // -- END Fragment methods

    // -- BEGIN BaseView methods
    @Override
    public void showProgress() {
        Log.d("SHOW PROGRESS", "Retrieving...");
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this.getActivity(), "Retrieved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Log.d("SHOW ERROR", message);
    }
    // -- END BaseView methods

    // -- BEGIN ItemContract.View methods
    @Override
    public void displayItem(ItemModel item) {
        Log.d("Display item", item.getName());
        tvItemName.setText(item.getName());
    }
    // -- END ItemContract.View methods
}