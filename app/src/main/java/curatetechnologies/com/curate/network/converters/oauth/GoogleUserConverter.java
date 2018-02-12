package curatetechnologies.com.curate.network.converters.oauth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/12/18.
 */

public class GoogleUserConverter {

    public static UserModel apply(GoogleSignInAccount account){
        // TODO: actually convert
        return new UserModel();
    }
}
