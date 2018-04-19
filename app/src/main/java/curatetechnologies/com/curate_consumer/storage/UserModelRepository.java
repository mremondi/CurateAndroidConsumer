package curatetechnologies.com.curate_consumer.storage;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.TagTypeModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;

/**
 * Created by mremondi on 2/13/18.
 */

public interface UserModelRepository {

    UserModel loginUserEmailPassword(String email, String password);
    UserModel registerUserEmailPassword(String email, String password);
    String loginWithFacebook(String accessToken);
    String loginWithGoogle(String accessToken);
    Boolean saveUser(UserModel userModel, boolean remote);
    Boolean saveUserPreferences(UserModel userModel, List<TagTypeModel> preferences);
    UserModel getCurrentUser();
    Boolean checkUsernameAvailable(String username);
    void signOutUser();
    UserModel getUserById(Integer userId);
    Integer getUserIdByEmail(String email);

}
