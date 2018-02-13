package curatetechnologies.com.curate.storage;

/**
 * Created by mremondi on 2/13/18.
 */

public interface UserModelRepository {

    String loginUserEmailPassword(String email, String password);
}
