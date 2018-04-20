package curatetechnologies.com.curate_consumer.network.converters.curate;

import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.network.model.CurateAPILoginUser;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIUserGet;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIUserPost;
import curatetechnologies.com.curate_consumer.network.model.CurateRegisterUser;

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
                userModel.getProfilePictureURL(), userModel.getFacebookToken(), "",
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

    public static UserModel convertLoginUserToUserModel(CurateAPILoginUser.User loginUser, String jwt){

        int id = loginUser.getID();
        String username = loginUser.getUsername()== null ? "" : loginUser.getUsername();
        String email = loginUser.getEmail() == null ? "" : loginUser.getEmail();
        Integer loyaltyPoints = loginUser.getLoyaltyPoints() == null ? 0 : (int) loginUser.getLoyaltyPoints();
        String fullName = loginUser.getFullName()== null ? "" : loginUser.getFullName();
        String firstName = loginUser.getFirstName() == null ? "" : loginUser.getFirstName();
        String lastName = loginUser.getLastName() == null ? "" : loginUser.getLastName();
        String DOB = loginUser.getDOB() == null ? "" : loginUser.getDOB();
        Integer age = loginUser.getAge() == null ? 0 : loginUser.getAge();
        String gender = loginUser.getGender() == null ? "" : loginUser.getGender();
        String profilePicture = loginUser.getPicture() == null ? "" : loginUser.getPicture();
        String facebookToken = loginUser.getFacebookToken() == null ? "" : loginUser.getFacebookToken();
        String googleToken = loginUser.getGoogleToken() == null ? "" : loginUser.getGoogleToken();
        String stripeId = loginUser.getStripeId() == null ? "" : loginUser.getStripeId();
        return new UserModel(id, username, email,
                loyaltyPoints, fullName, firstName,
                lastName, DOB, age, gender,
                profilePicture, facebookToken, googleToken,
                jwt, stripeId);
    }
}
