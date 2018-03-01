package curatetechnologies.com.curate.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;

/**
 * Created by mremondi on 2/22/18.
 */

public interface PostModelRepository {

    List<PostModel> getPostsByLocation(Integer limit, Location location, Float radius);

    List<PostModel> getPostsByUserId(Integer limit, Integer userId);

    List<PostModel> getPostsByRestaurantId(Integer limit, Integer restaurantId, String postType);
}
