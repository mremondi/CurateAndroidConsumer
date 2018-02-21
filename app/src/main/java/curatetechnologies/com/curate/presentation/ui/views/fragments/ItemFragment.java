package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;
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

    private ItemModel mItem;

    Unbinder unbinder;

    @BindView(R.id.fragment_item_title)
    TextView tvItemTitle;
    @BindView(R.id.fragment_item_photo_main)
    ImageView ivItemPhotoMain;
    @BindView(R.id.fragment_item_item_name)
    TextView tvItemName;
    @BindView(R.id.fragment_item_item_description)
    TextView tvItemDescription;
    @BindView(R.id.fragment_item_item_info_restaurant_name)
    TextView tvRestaurantName;
    @BindView(R.id.fragment_item_item_info_restaurant_address)
    TextView tvRestaurantAddress;
    @BindView(R.id.fragment_item_item_info_menu_name)
    TextView tvMenuName;
    @BindView(R.id.fragment_item_item_info_tag_names)
    TextView tvTags;

    @BindView(R.id.fragment_item_item_price)
    TextView tvItemPrice;

    @OnClick(R.id.fragment_item_add_to_cart_button) void onAddToCartClick(View view){
        Log.d("CLICKED", "ADD TO CART");
    }

    @OnClick(R.id.fragment_item_restaurant_row) void onRestaurantRowClick(View view){
        Log.d("CLICKED", "RESTAURANT ROW");
        Fragment restaurantFragment = new RestaurantFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(RestaurantFragment.RESTAURANT_ID, mItem.getRestaurantId());
        restaurantFragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame, restaurantFragment);
        transaction.commit();
    }

    @OnClick(R.id.fragment_item_menu_row) void onMenuRowClick(){
        Log.d("CLICKED", "Menu ROW");
    }

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
        mItem = item;
        tvItemTitle.setText(item.getName() + " - " + item.getRestaurantName());
        tvItemName.setText(item.getName());
        if (item.getImageURL() != null){
            Glide.with(this)
                    .load(item.getImageURL())
                    .into(ivItemPhotoMain);
        }
        tvItemDescription.setText(item.getDescription());
        tvRestaurantName.setText(item.getRestaurantName());
        tvMenuName.setText(item.getMenuName() + " - " + item.getMenuSectionName());
        tvItemPrice.setText(item.getPrice());
    }
    // -- END ItemContract.View methods
}