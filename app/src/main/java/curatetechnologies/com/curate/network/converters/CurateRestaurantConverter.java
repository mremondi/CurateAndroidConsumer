package curatetechnologies.com.curate.network.converters;

import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.network.model.CurateAPIRestaurant;

/**
 * Created by mremondi on 2/12/18.
 */

public class CurateRestaurantConverter {

    public static RestaurantModel convertCurateRestaurantToRestaurantModel(CurateAPIRestaurant apiRestaurant){
        return new RestaurantModel(apiRestaurant.getRestaurantID(), apiRestaurant.getRestaurantName(),
                                    apiRestaurant.getRestaurantLogoURL());
    }
}
