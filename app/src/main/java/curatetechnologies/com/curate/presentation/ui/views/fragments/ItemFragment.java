package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

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
import curatetechnologies.com.curate.presentation.ui.views.subclasses.RoundedCornerTransformation;
import curatetechnologies.com.curate.storage.ItemRepository;
import curatetechnologies.com.curate.storage.LocationRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class ItemFragment extends Fragment implements ItemContract.View {

    public static final String ITEM_ID = "itemId";
    Unbinder unbinder;

    private int progressStatus = 0;
    private Handler handler = new Handler();
    @BindView(R.id.fragment_item_progress_bar)
    ProgressBar progressBar;

    private ItemPresenter mItemPresenter;

    private ItemModel mItem;


    @BindView(R.id.fragment_item_item_info_primary)
    RelativeLayout itemPrimaryInfo;
    @BindView(R.id.item_info_secondary)
    LinearLayout itemInfoSecondary;
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
    @BindView(R.id.fragment_item_item_info_distance)
    TextView tvDistance;

    @BindView(R.id.fragment_item_item_price)
    TextView tvItemPrice;

    @OnClick(R.id.fragment_item_add_to_cart_button) void onAddToCartClick(View view){
        Log.d("CLICKED", "ADD TO CART");
    }

    @OnClick(R.id.fragment_item_restaurant_row) void onRestaurantRowClick(View view){
        Fragment restaurantFragment = new RestaurantFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(RestaurantFragment.RESTAURANT_ID, mItem.getRestaurantId());
        restaurantFragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(restaurantFragment, "RESTAURANT")
                .addToBackStack("RESTAURANT")
                .replace(R.id.content_frame, restaurantFragment)
                .commit();

    }

    @OnClick(R.id.fragment_item_menu_row) void onMenuRowClick(){
        Fragment menuFragment = new MenuFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MenuFragment.MENU_ID, mItem.getMenuId());
        menuFragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(menuFragment, "MENU")
                .addToBackStack("MENU")
                .replace(R.id.content_frame, menuFragment)
                .commit();
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

        mItemPresenter.getItemById(itemId, getLocation());
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
        itemInfoSecondary.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        itemInfoSecondary.setVisibility(View.VISIBLE);


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
        tvItemTitle.setText(item.getRestaurantName());


        tvItemName.setText(item.getName());
        if (item.getImageURL() != null){
            Glide.with(this)
                    .load(item.getImageURL())
                    .apply(bitmapTransform(new MultiTransformation(
                            new CenterCrop(), new RoundedCornerTransformation(45, 0,
                            RoundedCornerTransformation.CornerType.ALL))))
                    .into(ivItemPhotoMain);
        }
        tvItemDescription.setText(item.getDescription());
        tvRestaurantName.setText(item.getRestaurantName());
        tvDistance.setText(item.getDistance_in_mi());
        tvMenuName.setText(item.getMenuName() + " - " + item.getMenuSectionName());
        tvItemPrice.setText(item.getPrice());
    }
    // -- END ItemContract.View methods

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }
}