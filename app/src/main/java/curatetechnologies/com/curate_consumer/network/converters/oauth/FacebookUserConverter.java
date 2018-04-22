package curatetechnologies.com.curate_consumer.network.converters.oauth;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;

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
            String gender = userData.has("gender") ? userData.getString("gender") : "";
            String birthday = "01/01/1901";
            String profilePictureUrl = "https://graph.facebook.com/" + facebookId + "/picture?width=500&height=500";
            //userData.getJSONObject("picture").getJSONObject("data").getString("url");
            return new UserModel(0, "", email, 0, full_name, first_name, last_name,
                    birthday, 0, gender, profilePictureUrl, accessToken, "", "", "");
        } catch (JSONException e){
            Log.d("EXCEPTION", e.getLocalizedMessage());
        }
        return null;
    }
}