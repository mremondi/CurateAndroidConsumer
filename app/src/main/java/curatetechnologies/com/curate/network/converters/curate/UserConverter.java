package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.network.model.CurateAPIUser;
import curatetechnologies.com.curate.network.model.CurateRegisterUser;

/**
 * Created by mremondi on 2/15/18.
 */

public class UserConverter {

    public static UserModel convertCurateUserToUserModel(CurateAPIUser apiUser, String jwt){
        UserModel.Gender gender;
        if (apiUser.getUserGender().equals("MALE")){
            gender = UserModel.Gender.MALE;
        } else if (apiUser.getUserGender().equals("FEMALE")){
            gender = UserModel.Gender.FEMALE;
        } else{
            gender = UserModel.Gender.UNKNOWN;
        }
        return new UserModel(apiUser.getUserID(), apiUser.getUserUsername(), apiUser.getUserEmail(),
                apiUser.getUserLoyaltyPoints(), apiUser.getUserFullName(), apiUser.getUserFirstName(),
                apiUser.getUserLastName(), apiUser.getUserDOB(), apiUser.getUserAge(), gender,
                apiUser.getUserPicture(), apiUser.getUserFacebookToken(), apiUser.getUserGoogleToken(),
                jwt);
    }


    public static UserModel convertRegisteredUserToUserModel(CurateRegisterUser registerUser, String jwt){
        CurateRegisterUser.CurateSimpleAPIUser apiUser = registerUser.getUser();
        // TODO: grab ID to update correctly after onboarding
        Log.d("CURATE API USER", apiUser.getEmail());
        Log.d("CURATE TOKEN", registerUser.getToken());
        return new UserModel(apiUser.getId(), "", apiUser.getEmail(),
                0, "", "",
                "", "", 0, UserModel.Gender.UNKNOWN,
                "", "", "",
                jwt);
    }
}
