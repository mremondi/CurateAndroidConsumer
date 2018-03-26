package curatetechnologies.com.curate.config;

/**
 * Created by mremondi on 3/26/18.
 */

public class Utils {

    public static String getCurrentTimeString(){
        long millis = System.currentTimeMillis();
        return String.valueOf(millis);
    }
}
