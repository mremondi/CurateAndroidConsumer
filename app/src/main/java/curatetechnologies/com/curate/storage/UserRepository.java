package curatetechnologies.com.curate.storage;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.converters.curate.UserConverter;
import curatetechnologies.com.curate.network.model.CurateAPIUser;
import curatetechnologies.com.curate.network.model.CurateRegisterUser;
import curatetechnologies.com.curate.network.services.UserService;
import curatetechnologies.com.curate.storage.local.UserRoomDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mremondi on 2/13/18.
 */

public class UserRepository implements UserModelRepository {

    private static UserRepository INSTANCE = null;

    Context appContext;

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
            Log.d("REPO", response.body().toString());
            Log.d("JWT", response.body().getToken());
            Log.d("User", response.body().getUser().getEmail());
            jwt = response.body().getToken();
            user = UserConverter.convertRegisteredUserToUserModel(response.body(), jwt);
            return user;
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return user;
    }

    @Override
    public Boolean saveUser(final UserModel userModel) {
        // save user to DB
        Log.d("HERE", "in save user");
        UserService userService = CurateClient.getService(UserService.class);

        // TODO: get the NETWORK call working!
        //CurateAPIUser user = new CurateAPIUser();
        //Call<Integer> saveUser = userService.createUser(user);
        //String bearerToken = "Bearer " + userModel.getCurateToken();
        //saveUser.request().newBuilder().addHeader("Authorization", bearerToken);
        //Response<Integer> response = saveUser.execute();
        //Log.d("SUCCESS!", response.body().toString());
       // userModel.setId(response.body());
        // Cache userModel locally
        Log.d("HERE", "in save user try");
        return this.cacheUser(userModel);
    }

    private Boolean cacheUser(UserModel userModel){
        UserRoomDB db = Room.databaseBuilder(this.appContext,
                UserRoomDB.class, UserRoomDB.DB_NAME).build();
        db.userDAO().insertUser(userModel);
        return true;
    }

    @Override
    public Boolean saveUserPreferences(List<TagTypeModel> preferences){
        // TODO: get the NETWORK call working!
        return true;
    }

    private Boolean cachePreferences(List<TagTypeModel> preferences){
        // TODO: get the CACHE call working!
        return true;
    }



    public UserModel getCurrentUser(){
        UserRoomDB db = Room.databaseBuilder(this.appContext,
                UserRoomDB.class, UserRoomDB.DB_NAME).build();
        return db.userDAO().getUser();
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
}
