package curatetechnologies.com.curate.storage.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import curatetechnologies.com.curate.domain.model.UserModel;

/**
 * Created by mremondi on 2/15/18.
 */

/**
 * The Room Database that contains the Task table.
 */
@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class UserRoomDB extends RoomDatabase{

    private static UserRoomDB INSTANCE;
    public static String DB_NAME = "curate";
    public final static String TABLE_NAME = "UserModel";

    public abstract UserDAO userDAO();

    private static final Object sLock = new Object();

    public static UserRoomDB getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserRoomDB.class, DB_NAME)
                        .build();
            }
            return INSTANCE;
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
