package curatetechnologies.com.curate.network.converters.oauth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/12/18.
 */

public class GoogleUserConverter {

    public static UserModel apply(GoogleSignInAccount account){
        String email = account.getEmail();
        String full_name = account.getDisplayName();
        String first_name = account.getGivenName();
        String last_name = account.getFamilyName();
        String gender = "";
        String birthday = "01/01/1901";
        String profilePictureUrl = (account.getPhotoUrl() != null) ? account.getPhotoUrl().toString() : "";

        return new UserModel(0, "", email, 0, full_name, first_name, last_name,
                birthday, 0, gender, profilePictureUrl, "", "", account.getIdToken());
    }
}
