package curatetechnologies.com.curate.network.converters.curate;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.network.model.CurateAPIPost;

/**
 * Created by mremondi on 2/22/18.
 */

public class PostConverter {

    public static PostModel convertCuratePostToPostModel(CurateAPIPost apiPost){
        return new PostModel(apiPost.getPostID(), apiPost.getPostPostType(),
                apiPost.getRestaurantID(), apiPost.getItemID(), apiPost.getPostDescription(),
                apiPost.getPostRating(), apiPost.getPostNumberOfSaves(),
                apiPost.getPostNumberOfSaves(), apiPost.getPostImageURL(), apiPost.getPostTime(),
                apiPost.getUserID(), apiPost.getUserUsername(), apiPost.getUserPicture(),
                apiPost.getItemName(), apiPost.getRestaurantName(), apiPost.getDistanceInMiles());
    }
}
