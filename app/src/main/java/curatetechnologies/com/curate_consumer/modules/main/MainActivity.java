package curatetechnologies.com.curate_consumer.modules.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import curatetechnologies.com.curate_consumer.BuildConfig;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.manager.CartManager;
import curatetechnologies.com.curate_consumer.modules.cart.CartFragment;
import curatetechnologies.com.curate_consumer.modules.create_account.CreateAccountActivity;
import curatetechnologies.com.curate_consumer.presentation.Utils;
import curatetechnologies.com.curate_consumer.modules.rate_previous_order.RatePreviousOrderDialog;
import curatetechnologies.com.curate_consumer.modules.cart.EmptyCartFragment;
import curatetechnologies.com.curate_consumer.modules.feed.FeedFragment;
import curatetechnologies.com.curate_consumer.modules.more.MoreFragment;
import curatetechnologies.com.curate_consumer.modules.profile.ProfileFragment;
import curatetechnologies.com.curate_consumer.modules.search.SearchFragment;
import curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses.CartButtonWrapper;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;
import curatetechnologies.com.curate_consumer.storage.OrderRepository;
import curatetechnologies.com.curate_consumer.storage.StripeRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    public static final String GOTO_FRAGMENT_TAG = "GOTO_FRAGMENT_TAG";
    public static final String FEED_FRAGMENT_TAG = "FEED_FRAGMENT_TAG";
    public static final String SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT_TAG";
    public static final String PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";
    public static final String MORE_FRAGMENT_TAG = "MORE_FRAGMENT_TAG";

    private MainActivityContract mMainActivityPresenter;

    FusedLocationProviderClient mFusedLocationProvider;
    protected Location mLastLocation;

    @BindView(R.id.activity_main_cart_view)
    RelativeLayout cartBtnLayout;
    @BindView(R.id.activity_main_cart_price)
    TextView tvCartPrice;
    @BindView(R.id.activity_main_cart_restaurant_name)
    TextView tvCartRestaurantName;
    @BindView(R.id.cart_button)
    ImageButton btnCart;
    @BindView(R.id.cart_badge)
    TextView tvCartBadge;

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
                    if (UserRepository.getInstance(getApplicationContext()).getCurrentUser() == null){
                        presentMustBeLoggedInAlert();
                        return false;
                    } else {
                        Fragment profileFragment = new ProfileFragment();
                        transaction.replace(R.id.content_frame, profileFragment);
                        transaction.commit();
                        return true;
                    }
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

        mMainActivityPresenter = new MainActivityPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext()),
                new OrderRepository()
        );

        // ask user to rate their last order
        getLastOrder();

        // get location
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }

        // TODO: load the User and cache it. Eventually, we don't want to be doing this every time
        // TODO: but this makes sure that the StripeID is loaded and all info is current
        UserModel user = UserRepository.getInstance(getApplicationContext()).getCurrentUser();
        if (user != null) {
            mMainActivityPresenter.getUserById(user.getId());
        }

        Utils.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_search);


        CartButtonWrapper cartButtonWrapper = new CartButtonWrapper(getSupportFragmentManager(),
                cartBtnLayout, tvCartPrice, tvCartRestaurantName,
                btnCart, tvCartBadge);
        CartManager.getInstance().setGlobalCartButton(cartButtonWrapper);

        navigateToSpecifiedFragment();
    }

    private void navigateToSpecifiedFragment(){
        // USED FOR NAVIGATION FROM OTHER ACTIVITIES
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String gotoFragment = extras.getString(GOTO_FRAGMENT_TAG, "");

            if (gotoFragment.equals("") || gotoFragment.equals(SEARCH_FRAGMENT_TAG)) {
                Fragment searchFragment = new SearchFragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content_frame, searchFragment);
                transaction.commit();
            }
            else if (gotoFragment.equals(FEED_FRAGMENT_TAG)){
                Fragment feedFragment = new FeedFragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content_frame, feedFragment);
                transaction.commit();
            } else if (gotoFragment.equals(PROFILE_FRAGMENT_TAG)){
                Fragment profileFragment = new ProfileFragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content_frame, profileFragment);
                transaction.commit();
            } else if (gotoFragment.equals(MORE_FRAGMENT_TAG)){
                Fragment moreFragment = new MoreFragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content_frame, moreFragment);
                transaction.commit();
            }
        } else{
            Fragment searchFragment = new SearchFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content_frame, searchFragment);
            transaction.commit();
        }
    }

    private void getLastOrder(){
        mMainActivityPresenter.getLastOrder(getApplicationContext());
    }

    @Override
    public void rateLastOrder(OrderModel orderModel) {
        if (orderModel != null){
            // CREATE RATE ORDER MODAL
            RatePreviousOrderDialog ratePreviousOrderDialog =new RatePreviousOrderDialog(this, orderModel);
            ratePreviousOrderDialog.show();
        }
    }

    // SETS UP A CUSTOMER SESSION WITH STRIPE AND OUR BACKEND
    private void initializeStripeCustomer(final String email, final String customerId){
        PaymentConfiguration.init(BuildConfig.STRIPE_ID);
        CustomerSession.initCustomerSession(
                new EphemeralKeyProvider() {
                    @Override
                    public void createEphemeralKey(@NonNull String apiVersion,
                                                   @NonNull EphemeralKeyUpdateListener keyUpdateListener) {
                        StripeRepository stripeRepository = new StripeRepository();
                        String token = stripeRepository.createEphemeralKey(apiVersion,
                                email,
                                customerId,
                                keyUpdateListener);
                    }
                });
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
                            mLastLocation = LocationRepository.getInstance(getApplicationContext()).getTestingLocation();
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


    @Override
    public void updateUser(UserModel userModel) {
        // TODO: stop doing all of this every time
        userModel.setCurateToken(UserRepository.getInstance(getApplicationContext()).getCurrentUser().getCurateToken());
        UserRepository.getInstance(getApplicationContext()).saveUser(userModel, false);
        UserModel user = UserRepository.getInstance(getApplicationContext()).getCurrentUser();
        if (user != null){
            Log.d("HERE", user.getEmail());
            Log.d("STRIPE ID", user.getStripeId());
            if (!user.getEmail().equals("") || user.getEmail() != null){
                initializeStripeCustomer(user.getEmail(), user.getStripeId());
            }
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    private void presentMustBeLoggedInAlert(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        final Context context = this;
        builder.setTitle("Please Log In")
                .setMessage("You need to be logged in to use this feature.")
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context, CreateAccountActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
