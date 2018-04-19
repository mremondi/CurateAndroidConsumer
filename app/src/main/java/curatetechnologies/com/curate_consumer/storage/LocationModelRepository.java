package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

/**
 * Created by mremondi on 2/20/18.
 */

public interface LocationModelRepository {

    void setRadius(Float radius);
    Float getRadius();
    void setLastLocation(Location location);
    Location getLastLocation();
}
