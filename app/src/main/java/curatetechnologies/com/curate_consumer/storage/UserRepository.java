package curatetechnologies.com.curate_consumer.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.stripe.android.CustomerSession;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.config.Constants;
import curatetechnologies.com.curate_consumer.domain.model.TagTypeModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.converters.curate.UserConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPILoginUser;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIPreferencePost;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIUserGet;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIUserPost;
import curatetechnologies.com.curate_consumer.network.model.CurateRegisterUser;
import curatetechnologies.com.curate_consumer.network.services.UserService;
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
    public UserModel registerUserEmailPassword(String email, String password) {
        String jwt;
        UserModel user = null;
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<CurateRegisterUser> response = userService.registerUserEmail(email, password).execute();
            Log.d("REGISTER RESPONSE", response.body().toString());
            jwt = response.body().getToken();
            user = UserConverter.convertRegisteredUserToUserModel(response.body(), jwt);
            return user;
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return user;
    }

    @Override
    public UserModel loginUserEmailPassword(String email, String password) {
        String jwt;
        UserModel user = null;
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<CurateAPILoginUser> response = userService.loginUserEmail(email, password).execute();

            Log.d("USER LOGGED IN", response.body().toString());
            jwt = response.body().getToken();
            user = UserConverter.convertLoginUserToUserModel(response.body().getUser(), jwt);
            return user;
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return user;
    }

    @Override
    public String loginWithFacebook(String accessToken) {
        String jwt = "";
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<JsonObject> response = userService.loginUserFacebook(accessToken).execute();

            jwt = response.body().get("token").getAsString();
            return jwt;
        } catch (Exception e){
            Log.d("FAILURE LoginFacebook", e.getMessage());
        }
        return jwt;
    }

    @Override
    public String loginWithGoogle(String accessToken) {
        String jwt = "";
        Log.d("ACCESS TOKEN GOOGLE" , accessToken);
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<JsonObject> response = userService.loginUserGoogle(accessToken).execute();

            jwt = response.body().get("token").getAsString();
            return jwt;
        } catch (Exception e) {
            Log.d("FAILURE LoginGoogle", e.getMessage());
        }
        return jwt;
    }

    @Override
    public Boolean saveUser(final UserModel userModel, boolean remote, boolean isSocialLogin) {
        // save user to DB
        UserService userService = CurateClient.getService(UserService.class);
        if (remote) {
            try {
                // NOTE: NEED TO PUT A NUMBER OTHER THAN 0 (returns false in bool test in JS)
                // -1 is never used though
                if (isSocialLogin) {
                    userModel.setId(-1);
                }
                userModel.setGender("");
                CurateAPIUserPost user = UserConverter.convertUserModelToCurateUserPost(userModel);

                // NOTE: Don't save google id_token on our server. Too big currently for our schema
                // and isn't really used again
                user.setGoogleToken("");

                String bearerToken = "Bearer " + userModel.getCurateToken();
                Call<JsonObject> saveUser = userService.createUser(bearerToken, user);
                Response<JsonObject> response = saveUser.execute();

                if (isSocialLogin){
                    userModel.setId(response.body().get("userID").getAsInt());
                }
                return this.cacheUser(userModel);

            } catch (Exception e) {
                Log.d("network save user", "failure " + e.getLocalizedMessage());
                return false;
            }
        }
        return this.cacheUser(userModel);
    }

    private Boolean cacheUser(UserModel userModel){
        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        Log.d("USER CACHE", json);
        prefsEditor.putString(Constants.USER_SHARED_PREFERENCE_KEY, json);
        prefsEditor.apply();

        return true;
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

    @Override
    public UserModel getUserById(Integer userId) {
        UserModel user = null;
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<CurateAPIUserGet> response = userService
                    .getUserById(userId)
                    .execute();
            Log.d("STRIPE ID RESPONSE", "ID " + response.body().getUserStripeId());
            user = UserConverter.convertCurateUserToUserModel(response.body());
        } catch (Exception e){
            Log.d("FAILURE1", e.getMessage());
        }
        return user;
    }

    @Override
    public Integer getUserIdByEmail(String email) {
        Integer userId;
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<JsonObject> response = userService
                    .getUserIdByEmail(email)
                    .execute();
            userId = response.body().get("ID").getAsInt();
        } catch (Exception e){
            Log.d("FAILURE GET ID BY EMAIl", e.getMessage());
            return 0;
        }
        return userId;
    }
}
