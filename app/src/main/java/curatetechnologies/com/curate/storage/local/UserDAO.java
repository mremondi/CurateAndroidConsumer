package curatetechnologies.com.curate.storage.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/15/18.
 */
@Dao
public interface UserDAO {

    /**
     * Select all Users from the User table.
     *
     * @return all users.
     */
    @Query("SELECT * FROM " + UserRoomDB.TABLE_NAME)
    UserModel getUser();


    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserModel user);

    /**
     * Update a user.
     *
     * @param user task to be updated
     * @return the number of users updated. This should always be 1.
     */
    @Update
    int updateUser(UserModel user);

}
