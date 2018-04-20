package curatetechnologies.com.curate_consumer.config;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by mremondi on 3/26/18.
 */

public class Utils {

    public static String getCurrentTimeString(){
        long millis = System.currentTimeMillis();
        return String.valueOf(millis);
    }
}
