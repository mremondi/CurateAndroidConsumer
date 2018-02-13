package curatetechnologies.com.curate.storage;

import android.util.Log;

import org.json.JSONObject;

import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.model.CurateAPIUser;
import curatetechnologies.com.curate.network.model.CurateRegisterUser;
import curatetechnologies.com.curate.network.services.UserService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/13/18.
 */

public class UserRepository implements UserModelRepository {


    @Override
    public String loginUserEmailPassword(String email, String password) {
        String jwt = "";
        UserService userService = CurateClient.getService(UserService.class);
        try {
            Response<CurateRegisterUser> response = userService.registerUserEmail(email, password).execute();
            jwt = response.body().getToken();
            return jwt;
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return jwt;
    }
}
