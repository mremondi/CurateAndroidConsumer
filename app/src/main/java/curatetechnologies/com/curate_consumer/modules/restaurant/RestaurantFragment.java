package curatetechnologies.com.curate_consumer.modules.restaurant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.modules.edit_image.EditImageActivity;
import curatetechnologies.com.curate_consumer.modules.item_preview_bottom_sheet.ItemPreviewBottomSheetFragment;
import curatetechnologies.com.curate_consumer.modules.menu.MenuFragment;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.ImagePostAdapter;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.RestaurantMenusAdapter;
import curatetechnologies.com.curate_consumer.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;
import curatetechnologies.com.curate_consumer.storage.PostRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

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
    @BindView(R.id.fragment_restaurant_restaurant_name)
    TextView tvRestaurantName;

    @BindView(R.id.fragment_restaurant_rating_pie)
    PieChart ratingPie;

    // TODO: uncomment in xml too
    //@BindView(R.id.fragment_restaurant_restaurant_cuisines)
    //TextView tvRestaurantCuisines;

    @BindView(R.id.fragment_restaurant_open_closed_text)
    TextView tvOpenOrClosed;
    @BindView(R.id.fragment_restaurant_restaurant_ordering_enabled)
    TextView tvOrderingEnabled;
    @BindView(R.id.fragment_restaurant_restaurant_distance)
    TextView tvRestaurantDistance;


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

    @OnClick(R.id.fragment_restaurant_contact_button)
    void onContactClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mRestaurant.getPhoneNumber()));
        startActivity(intent);
    }

    @OnClick(R.id.fragment_restaurant_website_button)
    void onWebsiteClick() {
        String url = mRestaurant.getWebsiteURL();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.fragment_restaurant_directions_button)
    void onDirectionsClick() {


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

        mRestaurantPresenter.getRestaurantById(restaurantId, getLocation(), getRadius());
        mRestaurantPresenter.isRestaurantOpen(restaurantId);
        mRestaurantPresenter.getRestaurantPosts(20, restaurantId);

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        photosRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void displayRestaurant(final RestaurantModel restaurant) {
        mRestaurant = restaurant;
        enableButtons(true);

        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);

        tvRestaurantName.setText(restaurant.getName());

        // greater than 0 because testing equivalence in floats/doubles is weird
        if (restaurant.getRating() > 0) {
            ratingPie.setVisibility(View.VISIBLE);
            configurePieChart(restaurant);
        } else {
            ratingPie.setVisibility(View.INVISIBLE);
        }

        if (restaurant.getStripeID() == null || restaurant.getStripeID().equals("") ||
                restaurant.getStripeID().equals("undefined")) {
            tvOrderingEnabled.setTextColor(getResources().getColor(R.color.colorAccentLight));
            tvOrderingEnabled.setText("Ordering Not Enabled");
        } else {
            tvOrderingEnabled.setTextColor(getResources().getColor(R.color.selectedGreen));
            tvOrderingEnabled.setText("Ordering Enabled");
        }

        tvRestaurantDistance.setText(restaurant.getDistance_in_mi());
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
    public void displayOpenClosed(boolean isOpen) {
        if (isOpen) {
            tvOpenOrClosed.setText("Open");
            tvOpenOrClosed.setTextColor(getResources().getColor(R.color.activeBlue));
        } else {
            tvOpenOrClosed.setText("Closed");
            tvOpenOrClosed.setTextColor(getResources().getColor(R.color.colorAccentLight));
        }
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
                                        Intent i = new Intent(Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                                    }
                                });
                        alertDialog.show();
                    }
                }));
    }

    private boolean checkWritePermission() {
        return (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestWritePermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    private boolean checkReadPermission() {
        return (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestReadPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    private void enableButtons(boolean enabled) {
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
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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

    // -- BEGIN BASEVIEW CONTRACT METHODS


    @Override
    public boolean isActive() {
        return isAdded();
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

    private void configurePieChart(RestaurantModel restaurant) {
        List<PieEntry> entries = calculateRatingEntries(restaurant.getRating());
        PieDataSet set = new PieDataSet(entries, "");
        set.setDrawIcons(false);
        set.setDrawValues(false);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.LTGRAY);
        colors.add(getResources().getColor(R.color.selectedGreen));
        set.setColors(colors);
        set.setSelectionShift(0);
        PieData data = new PieData(set);

        ratingPie.setRotationEnabled(false);
        ratingPie.setHighlightPerTapEnabled(false);
        ratingPie.getLegend().setEnabled(false);
        ratingPie.setUsePercentValues(false);
        ratingPie.setDrawSlicesUnderHole(false);
        ratingPie.setDrawHoleEnabled(true);
        ratingPie.setHoleRadius(80f);
        ratingPie.getDescription().setEnabled(false);
        ratingPie.setDrawCenterText(true);
        double percentRating = restaurant.getRating() * 100;
        ratingPie.setCenterText(String.format("%.0f", percentRating));
        ratingPie.setData(data);
        ratingPie.invalidate(); // refresh
    }

    private List<PieEntry> calculateRatingEntries(Double rating) {
        float ratingf = rating.floatValue();
        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(1.0f - ratingf));
        entries.add(new PieEntry(ratingf));
        return entries;
    }

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }

    private Float getRadius(){
        return LocationRepository.getInstance(getContext()).getRadius();
    }

}


