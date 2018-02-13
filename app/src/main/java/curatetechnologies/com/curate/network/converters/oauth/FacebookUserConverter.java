package curatetechnologies.com.curate.network.converters.oauth;

import android.util.Log;

import com.facebook.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/12/18.
 */

public class FacebookUserConverter {

    public static UserModel apply(JSONObject userData, String accessToken){
        try{
            String facebookId = userData.has("id") ? userData.getString("id") : "";
            String email = userData.has("email") ? userData.getString("email") : "";
            String full_name = userData.has("name") ? userData.getString("name") : "";
            String first_name = userData.has("first_name") ? userData.getString("first_name") : "";
            String last_name = userData.has("last_name") ? userData.getString("last_name") : "";
            String g = userData.has("gender") ? userData.getString("gender") : "";
            UserModel.Gender gender;
            if (g.equals("male")){
                gender = UserModel.Gender.MALE;
            } else if (g.equals("female")){
                gender = UserModel.Gender.FEMALE;
            } else {
                gender = UserModel.Gender.UNKNOWN;
            }
            String birthday = "01/01/1901";
            String profilePictureUrl = "https://graph.facebook.com/" + facebookId + "/picture?width=500&height=500";
            //userData.getJSONObject("picture").getJSONObject("data").getString("url");
            return new UserModel(0, "", email, 0, full_name, first_name, last_name,
                    birthday, 0, gender, profilePictureUrl, "", accessToken, "");
        } catch (JSONException e){
            Log.d("EXCEPTION", e.getLocalizedMessage());
        }
        return null;
    }
}
