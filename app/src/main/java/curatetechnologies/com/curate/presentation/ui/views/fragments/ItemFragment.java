package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

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
import curatetechnologies.com.curate.presentation.ui.adapters.ImagePostAdapter;
import curatetechnologies.com.curate.presentation.ui.views.activities.EditImageActivity;
import curatetechnologies.com.curate.presentation.ui.views.activities.LoginActivity;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate.storage.ItemRepository;
import curatetechnologies.com.curate.storage.LocationRepository;
import curatetechnologies.com.curate.storage.PostRepository;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ItemFragment extends Fragment implements ItemContract.View {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    private Uri imageUri;

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

    @BindView(R.id.fragment_item_photos_recyclerview)
    RecyclerView photosRecyclerView;

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
                        mItem.getName(), mItem.getRestaurantName(), 0.0, null)
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
                        mItem.getName(), mItem.getRestaurantName(), 0.0, null)
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

        photosRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));

        Integer itemId = getArguments().getInt(ITEM_ID);

        mItemPresenter = new ItemPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new ItemRepository(),
                new PostRepository());

        mItemPresenter.getItemById(itemId, getLocation());
        mItemPresenter.getItemPosts(20, itemId);
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
    public void displayItemPosts(final List<PostModel> posts) {
        // add a null post to create a add photo button
        posts.add(null);
        final ItemFragment self = this;
        photosRecyclerView.setAdapter(new ImagePostAdapter(posts,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        ItemPreviewBottomSheetFragment bottomSheetFragment = new ItemPreviewBottomSheetFragment();
                        bottomSheetFragment.setPost(posts.get(position));
                        bottomSheetFragment.show(self.getFragmentManager(), bottomSheetFragment.getTag());
                    }
                },
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        // present dialog asking if they want to add a photo from camera or from gallery

                        if (!checkWritePermission())
                            requestWritePermission();
                        if (!checkReadPermission())
                            requestReadPermission();

                        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Add Photo");
                        alertDialog.setMessage("How would you like to add a new photo?");
                        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "Camera",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // CAMERA
                                        captureCameraImage();
                                    }
                                });
                        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "Gallery",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        // GALLERY
                                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                                    }
                                });
                        alertDialog.show();
                    }
                }));
    }

    private void captureCameraImage() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageUri = Uri.fromFile(f);
        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }



    @Override
    public void postCreatedSuccessfully() {
        Log.d("POST CREATED", "SUCCESSFULLY");
    }

    // -- END ItemContract.View methods

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }

    private boolean checkWritePermission(){
        return (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestWritePermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    private boolean checkReadPermission(){
        return (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestReadPermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap = null;

        Intent intent = new Intent(getContext(), EditImageActivity.class);
        intent.putExtra(EditImageActivity.IMAGE_URI, imageUri);
        intent.putExtra(EditImageActivity.ITEM_ID, mItem.getId());
        intent.putExtra(EditImageActivity.RESTAURANT_ID, mItem.getRestaurantId());

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(imageUri != null) {
                startActivity(intent);

            }
        }  else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            intent.putExtra(EditImageActivity.IMAGE_GALLERY_PATH, picturePath);
            startActivity(intent);

        }
        if (imageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();


        }
    }
}