package curatetechnologies.com.curate.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import curatetechnologies.com.curate.config.Constants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mremondi on 2/20/18.
 */

public class LocationRepository implements LocationModelRepository {

    private static LocationRepository INSTANCE = null;

    private Context appContext;

    private LocationRepository(Context appContext){
        this.appContext = appContext;
    }

    public static LocationRepository getInstance(Context appContext) {
        if (INSTANCE == null) {
            INSTANCE = new LocationRepository(appContext);
        }
        return INSTANCE;
    }

    @Override
    public void setRadius(Float radius) {
        SharedPreferences prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(Constants.RADIUS_SHARED_PREFERENCE_KEY, radius);
        prefsEditor.apply();
    }

    @Override
    public Float getRadius() {
        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        return prefs.getFloat(Constants.RADIUS_SHARED_PREFERENCE_KEY, 50);
    }

    @Override
    public void setLastLocation(Location location) {
        SharedPreferences prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(Constants.LATITUDE_SHARED_PREFERENCE_KEY, (float)location.getLatitude());
        prefsEditor.putFloat(Constants.LONGITUDE_SHARED_PREFERENCE_KEY, (float)location.getLongitude());
        prefsEditor.apply();
    }

    @Override
    public Location getLastLocation() {
        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        Float lat = prefs.getFloat(Constants.LATITUDE_SHARED_PREFERENCE_KEY, (float)42.444853);
        Float lon = prefs.getFloat(Constants.LONGITUDE_SHARED_PREFERENCE_KEY, (float)-71.147892);
        Location location = new Location(Constants.LOCATION_PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lon);
        return location;
    }
}
