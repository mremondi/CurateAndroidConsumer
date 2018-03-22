package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.network.model.CurateAPIUserGet;
import curatetechnologies.com.curate.network.model.CurateAPIUserPost;
import curatetechnologies.com.curate.network.model.CurateRegisterUser;

/**
 * Created by mremondi on 2/15/18.
 */

public class UserConverter {

    public static UserModel convertCurateUserToUserModel(CurateAPIUserGet apiUser){
        return new UserModel(apiUser.getUserID(), apiUser.getUserUsername(), apiUser.getUserEmail(),
                0, apiUser.getUserFullName(), apiUser.getUserFirstName(),
                apiUser.getUserLastName(), apiUser.getUserDOB(), apiUser.getUserAge(),
                apiUser.getUserGender(), apiUser.getUserPicture(), apiUser.getUserFacebookToken(),
                apiUser.getUserGoogleToken(), "", apiUser.getUserStripeId());
    }

    public static UserModel convertCurateUserToUserModel(CurateAPIUserGet apiUser, String jwt){
        return new UserModel(apiUser.getUserID(), apiUser.getUserUsername(), apiUser.getUserEmail(),
               0, apiUser.getUserFullName(), apiUser.getUserFirstName(),
                apiUser.getUserLastName(), apiUser.getUserDOB(), apiUser.getUserAge(),
                apiUser.getUserGender(), apiUser.getUserPicture(), apiUser.getUserFacebookToken(),
                apiUser.getUserGoogleToken(), jwt, apiUser.getUserStripeId());
    }

    public static CurateAPIUserPost convertUserModelToCurateUserPost(UserModel userModel){
        return new CurateAPIUserPost(userModel.getId(), userModel.getUsername(), userModel.getEmail(),
                userModel.getLoyaltyPoints(), userModel.getFirstName(), userModel.getFullName(),
                userModel.getLastName(), userModel.getBirthday(), userModel.getAge(), userModel.getGender(),
                userModel.getProfilePictureURL(), userModel.getFacebookToken(), userModel.getGoogleToken(),
                "");
    }

    public static UserModel convertRegisteredUserToUserModel(CurateRegisterUser registerUser, String jwt){
        CurateRegisterUser.CurateSimpleAPIUser apiUser = registerUser.getUser();
        return new UserModel(apiUser.getId(), "", apiUser.getEmail(),
                0, "", "",
                "", "01/01/1901", 0, "",
                "", "", "",
                jwt, "");
    }
}
