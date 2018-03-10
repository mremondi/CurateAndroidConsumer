package curatetechnologies.com.curate.storage;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.stripe.android.CustomerSession;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;
import com.stripe.android.PaymentConfiguration;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.config.Constants;
import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.converters.curate.UserConverter;
import curatetechnologies.com.curate.network.model.CurateAPIPreferencePost;
import curatetechnologies.com.curate.network.model.CurateAPIUserPost;
import curatetechnologies.com.curate.network.model.CurateRegisterUser;
import curatetechnologies.com.curate.network.services.UserService;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mremondi on 2/13/18.
 */

public class UserRepository implements UserModelRepository {

    private static UserRepository INSTANCE = null;

    private Context appContext;

    private UserRepository(Context appContext){
        this.appContext = appContext;
    }

    public static UserRepository getInstance(Context appContext) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(appContext);
        }
        return INSTANCE;
    }


    @Override
    public UserModel loginUserEmailPassword(String email, String password) {
        String jwt;
        UserModel user = null;
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<CurateRegisterUser> response = userService.registerUserEmail(email, password).execute();
            jwt = response.body().getToken();
            user = UserConverter.convertRegisteredUserToUserModel(response.body(), jwt);
            return user;
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return user;
    }

    @Override
    public Boolean saveUser(final UserModel userModel, boolean remote) {
        // save user to DB
        UserService userService = CurateClient.getService(UserService.class);
        if (remote) {
            try {
                CurateAPIUserPost user = UserConverter.convertUserModelToCurateUserPost(userModel);
                String bearerToken = "Bearer " + userModel.getCurateToken();
                Call<JsonObject> saveUser = userService.createUser(bearerToken, user);
                Response<JsonObject> response = saveUser.execute();
            } catch (Exception e) {
                Log.d("network save user", "failure " + e.getLocalizedMessage());
            }
        }
        return this.cacheUser(userModel);
    }

    private Boolean cacheUser(UserModel userModel){
        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        prefsEditor.putString(Constants.USER_SHARED_PREFERENCE_KEY, json);
        prefsEditor.apply();

        initializeStripeCustomer(userModel.getEmail());
        return true;
    }

    // SETS UP A CUSTOMER SESSION WITH STRIPE AND OUR BACKEND
    private void initializeStripeCustomer(final String email){
        PaymentConfiguration.init("pk_test_5mf0TR8Bf9NP6fXT3Mlg6DHv");
        CustomerSession.initCustomerSession(
                new EphemeralKeyProvider() {
                    @Override
                    public void createEphemeralKey(@NonNull String apiVersion,
                                                   @NonNull EphemeralKeyUpdateListener keyUpdateListener) {
                        StripeRepository stripeRepository = new StripeRepository();
                        String token = stripeRepository.createEphemeralKey(apiVersion,
                                email,
                                keyUpdateListener);
                    }
                });
    }

    @Override
    public Boolean saveUserPreferences(UserModel userModel, List<TagTypeModel> preferences){
        ArrayList<Integer> tagIds = new ArrayList<>();
        for (TagTypeModel preference: preferences){
            tagIds.add(preference.getId());
        }

        UserService userService = CurateClient.getService(UserService.class);
        try {
            String bearerToken = "Bearer " + userModel.getCurateToken();
            CurateAPIPreferencePost preferencePost = new CurateAPIPreferencePost(userModel.getId(), tagIds);
            Call<JsonNull> saveUserPreferences = userService.createUserPreferences(bearerToken, preferencePost);
            Response<JsonNull> response = saveUserPreferences.execute();
        } catch (Exception e) {
            Log.d("network save preference", "failure " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public UserModel getCurrentUser(){
        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(Constants.USER_SHARED_PREFERENCE_KEY, "");
        UserModel user = gson.fromJson(json, UserModel.class);
        return user;
    }

    public Boolean checkUsernameAvailable(String username){
        Boolean available;
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<JsonObject> response = userService.checkUsernameAvailable(username).execute();
            available = response.body().get("isAvailable").getAsBoolean();
            return available;
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
            available = false;
        }
        return available;
    }

    @Override
    public void signOutUser() {
        // if signed in with facebook
        LoginManager.getInstance().logOut();
        // if signed in with google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignIn.getClient(appContext, gso).signOut();
        // if signed in with curate
        // clear shared preferences
        SharedPreferences preferences = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        // clear stripe customer session
        CustomerSession.endCustomerSession();
    }
}
