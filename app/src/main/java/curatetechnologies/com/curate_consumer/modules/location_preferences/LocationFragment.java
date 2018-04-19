package curatetechnologies.com.curate_consumer.modules.location_preferences;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;


public class LocationFragment extends Fragment {

    Unbinder unbinder;
    private double mRadius = 50.0;

    @OnClick(R.id.fragment_location_detect_current_location_row) void onDetectLocationClick(){
        detectCurrentLocation();
    }

    @BindView(R.id.fragment_location_radius_seek_bar)
    SeekBar radiusSeekBar;
    @BindView(R.id.fragment_location_radius_text_view)
    TextView radiusTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        unbinder = ButterKnife.bind(this, v);


        setUpPlacePicker();
        setUpRadiusSeekBar();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity()
                        .getFragmentManager()
                        .findFragmentById(R.id.fragment_location_place_autocomplete_fragment);
        if (autocompleteFragment != null)
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .remove(autocompleteFragment)
                    .commit();
        unbinder.unbind();
        cacheRadius();
    }

    private void setUpPlacePicker(){
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity()
                        .getFragmentManager()
                        .findFragmentById(R.id.fragment_location_place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d("LAT", String.valueOf(place.getLatLng().latitude));
                Log.d("LON", String.valueOf(place.getLatLng().longitude));
                cacheLocation(place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });
    }

    private void setUpRadiusSeekBar(){
        radiusSeekBar.setProgress((int)Math.round(getCachedRadius()*2));
        radiusTextView.setText(String.valueOf(getCachedRadius()) + "mi");
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mRadius = i/2.0;
                String displayRadius = String.valueOf(mRadius) + "mi";
                radiusTextView.setText(displayRadius);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void cacheLocation(Place place){
        Location location = new Location("");
        location.setLatitude(place.getLatLng().latitude);
        location.setLongitude(place.getLatLng().longitude);

        LocationRepository.getInstance(getContext()).setLastLocation(location);
    }

    private void cacheRadius(){
        LocationRepository.getInstance(getContext()).setRadius((float) mRadius);
    }

    private double getCachedRadius(){
        return LocationRepository.getInstance(getContext()).getRadius();
    }

    private void detectCurrentLocation(){
        FusedLocationProviderClient fusedLocationProvider = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            fusedLocationProvider.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LocationRepository.getInstance(getContext()).setLastLocation(location);
                    }
                }
            });
        } catch (SecurityException e){
            // TODO: ask for permissions
        }
    }

}
