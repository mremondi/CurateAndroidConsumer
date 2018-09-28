package curatetechnologies.com.curate_consumer.modules.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

public class MapFragment extends Fragment implements
        MapPresenter.View,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{

    private MapContract mMapPresenter;

    Map<Marker, RestaurantModel> markerRestaurantMap;

    Unbinder unbinder;

    @BindView(R.id.fragment_map_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fragment_map_map_view)
    MapView mMapView;

    GoogleMap mGoogleMap;

    // -- BEGIN: Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        unbinder = ButterKnife.bind(this, v);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mMapPresenter = new MapPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    // -- END: Fragment Methods

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng latLng = new LatLng(getLocation().getLatitude(), getLocation().getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

        mGoogleMap.setOnMarkerClickListener(this);

        mMapPresenter.getNearbyRestaurants(getLocation(), getUserId(), getRadius());
    }

    // -- BEGIN: MapContract.View methods
    @Override
    public void displayRestaurants(List<RestaurantModel> restaurants) {
        addRestaurantsToMap(restaurants);
    }
    // -- END: MapContract.View methods

    private void addRestaurantsToMap(List<RestaurantModel> restaurants){
        markerRestaurantMap = new HashMap<>();
        for (RestaurantModel restaurant: restaurants) {
            LatLng latLng = restaurant.getRestaurantLocation();
            Marker m = mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            markerRestaurantMap.put(m, restaurant);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        RestaurantModel restaurantModel = markerRestaurantMap.get(marker);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(restaurantModel.getRestaurantLocation(), 12);
        mGoogleMap.animateCamera(cameraUpdate);

        MapPreviewBottomSheet bottomSheetFragment = new MapPreviewBottomSheet();
        bottomSheetFragment.setRestaurant(restaurantModel);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
        return true;
    }

    // -- BEGIN: BaseView methods
    @Override
    public void showProgress() {
        // Start the lengthy operation in a background thread
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Log.d("SHOW ERROR", message);
    }
    // -- END: BaseView methods

    private Integer getUserId(){
        UserRepository userRepository = UserRepository.getInstance(getContext());
        if (userRepository.getCurrentUser() != null){
            return userRepository.getCurrentUser().getId();
        } else {
            return null;
        }
    }

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }

    private Float getRadius(){
        return LocationRepository.getInstance(getContext()).getRadius();
    }
}

