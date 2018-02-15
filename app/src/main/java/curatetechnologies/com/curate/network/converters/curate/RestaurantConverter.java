package curatetechnologies.com.curate.network.converters.curate;

import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.network.model.CurateAPIRestaurant;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantConverter {

    public static RestaurantModel convertCurateRestaurantToRestaurantModel(CurateAPIRestaurant apiRestaurant){
        return new RestaurantModel(apiRestaurant.getRestaurantID(), apiRestaurant.getRestaurantName(),
                                    apiRestaurant.getRestaurantLogoURL(), "0.0mi");
    }
}
