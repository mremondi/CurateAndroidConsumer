package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.stripe.android.CustomerSession;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;
import com.stripe.android.PaymentConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.BuildConfig;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.presentation.ui.views.BottomNavigationViewHelper;
import curatetechnologies.com.curate.presentation.ui.views.fragments.FeedFragment;
import curatetechnologies.com.curate.presentation.ui.views.fragments.MoreFragment;
import curatetechnologies.com.curate.presentation.ui.views.fragments.ProfileFragment;
import curatetechnologies.com.curate.presentation.ui.views.fragments.SearchFragment;
import curatetechnologies.com.curate.storage.LocationRepository;
import curatetechnologies.com.curate.storage.StripeRepository;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    FusedLocationProviderClient mFusedLocationProvider;
    protected Location mLastLocation;

    @BindView(R.id.navigation) BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    Fragment feedFragment = new FeedFragment();
                    transaction.replace(R.id.content_frame, feedFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_search:
                    Fragment searchFragment = new SearchFragment();
                    transaction.replace(R.id.content_frame, searchFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_user_activity:
                    Fragment profileFragment = new ProfileFragment();
                    transaction.replace(R.id.content_frame, profileFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_more:
                    Fragment moreFragment = new MoreFragment();
                    transaction.replace(R.id.content_frame, moreFragment);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }

        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_search);

        Fragment searchFragment = new SearchFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame, searchFragment);
        transaction.commit();

    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationProvider.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            LocationRepository.getInstance(getApplicationContext()).setLastLocation(mLastLocation);
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar("No location detected.");
                        }
                    }
                });
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar("We need your location to help you find great food!", "OK",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar("Location permission denied. we need your location to help you find good food!",
                        "Settings",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.content_frame);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextString The id for the string resource for the Snackbar text.
     * @param actionString   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final String mainTextString, final String actionString,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(actionString, listener)
                .show();
    }

}
