package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;

/**
 * Created by mremondi on 2/22/18.
 */

public interface PostModelRepository {

    List<PostModel> getPostsByLocation(Integer limit, Location location, Float radius);

    List<PostModel> getPostsByUserId(Integer limit, Integer userId);

    List<PostModel> getPostsByRestaurantId(Integer limit, Integer restaurantId, String postType);

    List<PostModel> getPostsByItemId(Integer limit, Integer itemId, String postType);

    Integer createPost(String jwt, PostModel postModel);
}
