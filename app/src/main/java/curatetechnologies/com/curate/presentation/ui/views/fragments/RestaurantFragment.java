package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.presenters.RestaurantContract;
import curatetechnologies.com.curate.presentation.presenters.RestaurantPresenter;
import curatetechnologies.com.curate.presentation.ui.adapters.RestaurantMenusAdapter;
import curatetechnologies.com.curate.presentation.ui.adapters.ImagePostAdapter;
import curatetechnologies.com.curate.presentation.ui.views.activities.EditImageActivity;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate.storage.PostRepository;
import curatetechnologies.com.curate.storage.RestaurantRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mremondi on 2/21/18.
 */

public class RestaurantFragment extends Fragment implements RestaurantContract.View {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    public static final String RESTAURANT_ID = "restaurantId";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    @BindView(R.id.fragment_restaurant_progress_bar)
    ProgressBar progressBar;

    private RestaurantModel mRestaurant;

    private RestaurantPresenter mRestaurantPresenter;
    Unbinder unbinder;

    @BindView(R.id.fragment_restaurant_logo)
    ImageView ivRestaurantLogo;
    @BindView(R.id.fragment_restaurant_open_closed_text)
    TextView tvOpenOrClosed;
    @BindView(R.id.fragment_restaurant_todays_hours)
    TextView tvHoursOfOperations;
    @BindView(R.id.fragment_restaurant_menu_recyclerview)
    RecyclerView menuRecyclerView;
    @BindView(R.id.fragment_restaurant_photos_recyclerview)
    RecyclerView photosRecyclerView;
    @BindView(R.id.fragment_restaurant_contact_button)
    RelativeLayout btnContact;
    @BindView(R.id.fragment_restaurant_website_button)
    RelativeLayout btnWebsite;
    @BindView(R.id.fragment_restaurant_directions_button)
    RelativeLayout btnDirections;

    @OnClick(R.id.fragment_restaurant_contact_button) void onContactClick(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mRestaurant.getPhoneNumber()));
        startActivity(intent);
    }

    @OnClick(R.id.fragment_restaurant_website_button) void onWebsiteClick(){
        String url = mRestaurant.getWebsiteURL();
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.fragment_restaurant_directions_button) void onDirectionsClick(){


        String latLngQuery = "http://maps.google.com/maps?daddr=" +
                String.valueOf(mRestaurant.getRestaurantLocation().latitude) + ", " +
                String.valueOf(mRestaurant.getRestaurantLocation().longitude);


        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(latLngQuery));

        startActivity(intent);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant, container, false);

        unbinder = ButterKnife.bind(this, v);

        // makes sure no button is pressed before the restaurant is loaded, otherwise there would be a NPE
        enableButtons(false);

        Integer restaurantId = getArguments().getInt(RESTAURANT_ID);

        mRestaurantPresenter = new RestaurantPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository(),
                new PostRepository());

        mRestaurantPresenter.getRestaurantById(restaurantId);
        mRestaurantPresenter.getRestaurantPosts(20, restaurantId);

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        photosRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));

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

    @Override
    public void displayRestaurant(final RestaurantModel restaurant) {
        mRestaurant = restaurant;
        enableButtons(true);

        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);

        // TODO: populate these when we have the data coming in
        tvOpenOrClosed.setVisibility(View.GONE);
        tvHoursOfOperations.setVisibility(View.GONE);

        // set up recyclerview
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Integer menuId = restaurant.getMenus().get(position).getId();
                Fragment menuFragment = new MenuFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(MenuFragment.MENU_ID, menuId);
                menuFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .add(menuFragment, "MENU")
                        .addToBackStack("MENU")
                        .replace(R.id.content_frame, menuFragment)
                        .commit();
            }
        };
        menuRecyclerView.setAdapter(new RestaurantMenusAdapter(restaurant.getMenus(), listener));

    }

    @Override
    public void displayRestaurantPosts(final List<PostModel> posts) {
        final RestaurantFragment self = this;

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

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Add Photo");
                        alertDialog.setMessage("How would you like to add a new photo?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // CAMERA
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                        }
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery",
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

    private void enableButtons(boolean enabled){
        btnContact.setEnabled(enabled);
        btnDirections.setEnabled(enabled);
        btnWebsite.setEnabled(enabled);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // GET IMAGE DATA
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageBitmap = BitmapFactory.decodeFile(picturePath);
        }
        if (imageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent intent = new Intent(getContext(), EditImageActivity.class);
            intent.putExtra(EditImageActivity.IMAGE_URI, byteArray);
            startActivity(intent);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
