package curatetechnologies.com.curate.storage;

import android.util.Pair;

import java.util.List;

import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/13/18.
 */

public interface UserModelRepository {

    UserModel loginUserEmailPassword(String email, String password);
    Boolean saveUser(UserModel userModel);
    Boolean saveUserPreferences(List<TagTypeModel> preferences);
    UserModel getCurrentUser();
    Boolean checkUsernameAvailable(String username);

}
