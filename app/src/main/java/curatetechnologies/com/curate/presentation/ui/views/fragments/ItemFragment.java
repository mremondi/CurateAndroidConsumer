package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.presentation.presenters.ItemContract;
import curatetechnologies.com.curate.presentation.presenters.ItemPresenter;
import curatetechnologies.com.curate.presentation.ui.views.activities.LoginActivity;
import curatetechnologies.com.curate.storage.ItemRepository;
import curatetechnologies.com.curate.storage.LocationRepository;
import curatetechnologies.com.curate.storage.PostRepository;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ItemFragment extends Fragment implements ItemContract.View {

    public static final String ITEM_ID = "itemId";
    Unbinder unbinder;

    @BindView(R.id.fragment_item_progress_bar)
    ProgressBar progressBar;

    private ItemPresenter mItemPresenter;

    private ItemModel mItem;

    Boolean mLike;

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

    @BindView(R.id.fragment_item_thumbs_up)
    Button btnThumbsUp;
    @BindView(R.id.fragment_item_thumbs_down)
    Button btnThumbsDown;

    @OnClick(R.id.fragment_item_thumbs_down) void onDislike(){
        if (mLike == null){
            mLike = false;
        } else{
            mLike = !mLike;
        }
        updateRatingButtons();
        UserModel user = UserRepository
                .getInstance(getApplicationContext())
                .getCurrentUser();
        mItemPresenter.createRatingPost(user.getCurateToken(),
                new PostModel(0, "RATING", mItem.getRestaurantId(),
                        mItem.getId(), "", false, 0, 0, "",
                        "", user.getId(), user.getUsername(), user.getProfilePictureURL(),
                        mItem.getName(), mItem.getRestaurantName(), 0.0)
                );
    }

    @OnClick(R.id.fragment_item_thumbs_up) void onLike(){
        if (mLike == null){
            mLike = true;
        } else{
            mLike = !mLike;
        }
        updateRatingButtons();

        UserModel user = UserRepository
                .getInstance(getApplicationContext())
                .getCurrentUser();
        mItemPresenter.createRatingPost(user.getCurateToken(),
                new PostModel(0, "RATING", mItem.getRestaurantId(),
                        mItem.getId(), "", true, 0, 0, "",
                        "", user.getId(), user.getUsername(), user.getProfilePictureURL(),
                        mItem.getName(), mItem.getRestaurantName(), 0.0)
        );
    }

    private void updateRatingButtons(){
        if (mLike) {
            btnThumbsUp.setBackground(getResources().getDrawable(R.drawable.thumbs_up_black));
            btnThumbsDown.setBackground(getResources().getDrawable(R.drawable.thumbs_down));
        } else{
            btnThumbsUp.setBackground(getResources().getDrawable(R.drawable.thumbs_up));
            btnThumbsDown.setBackground(getResources().getDrawable(R.drawable.thumbs_down_black));
        }
    }

    @OnClick(R.id.fragment_item_add_to_cart_button) void onAddToCartClick(View view){
        if (UserRepository.getInstance(getContext()).getCurrentUser() == null){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Feature Unavailable")
                    .setMessage("You need to be logged in to use this feature.")
                    .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            CartManager.getInstance().addItemToCart(mItem);
        }
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
                new ItemRepository(),
                new PostRepository());

        mItemPresenter.getItemById(itemId, getLocation());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
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
                    .into(ivItemPhotoMain);
        }
        tvItemDescription.setText(item.getDescription());
        tvRestaurantName.setText(item.getRestaurantName());
        tvDistance.setText(item.getDistance_in_mi());
        tvMenuName.setText(item.getMenuName() + " - " + item.getMenuSectionName());
        tvItemPrice.setText(item.getPrice());
    }

    @Override
    public void postCreatedSuccessfully() {
        Log.d("POST CREATED", "SUCCESSFULLY");
    }

    // -- END ItemContract.View methods

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }
}