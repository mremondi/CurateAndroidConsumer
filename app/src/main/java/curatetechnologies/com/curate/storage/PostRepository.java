package curatetechnologies.com.curate.storage;

import android.location.Location;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.converters.curate.PostConverter;
import curatetechnologies.com.curate.network.model.CurateAPIPost;
import curatetechnologies.com.curate.network.services.PostService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/22/18.
 */

public class PostRepository implements PostModelRepository {

    @Override
    public List<PostModel> getPostsByLocation(Integer limit, Location location, Float radius) {

        final List<PostModel> posts = new ArrayList<>();

        // make network call
        PostService postService = CurateClient.getService(PostService.class);
        try {
            Response<List<CurateAPIPost>> response = postService
                    .getPostsByLocation(limit, location.getLatitude(), location.getLongitude(), radius)
                    .execute();
            for (CurateAPIPost post: response.body()){
                posts.add(PostConverter.convertCuratePostToPostModel(post));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return posts;
    }

    @Override
    public List<PostModel> getPostsByUserId(Integer limit, Integer userId) {
        final List<PostModel> posts = new ArrayList<>();

        // make network call
        PostService postService = CurateClient.getService(PostService.class);
        try {
            Response<List<CurateAPIPost>> response = postService
                    .getPostsByUserId(limit, userId)
                    .execute();
            for (CurateAPIPost post: response.body()){
                posts.add(PostConverter.convertCuratePostToPostModel(post));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return posts;
    }

    @Override
    public List<PostModel> getPostsByRestaurantId(Integer limit, Integer restaurantId, String postType) {
        final List<PostModel> posts = new ArrayList<>();

        // make network call
        PostService postService = CurateClient.getService(PostService.class);
        try {
            Response<List<CurateAPIPost>> response = postService
                    .getPostsByRestaurantId(limit, restaurantId, postType)
                    .execute();
            for (CurateAPIPost post: response.body()){
                posts.add(PostConverter.convertCuratePostToPostModel(post));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return posts;
    }

    @Override
    public List<PostModel> getPostsByItemId(Integer limit, Integer itemId, String postType) {
        final List<PostModel> posts = new ArrayList<>();

        PostService postService = CurateClient.getService(PostService.class);
        try {
            Response<List<CurateAPIPost>> response = postService
                    .getPostsByItemId(limit, itemId, postType)
                    .execute();
            for (CurateAPIPost post: response.body()){
                posts.add(PostConverter.convertCuratePostToPostModel(post));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return posts;
    }

    @Override
    public Integer createPost(String jwt, PostModel postModel) {
        Integer insertId = 0;
        String bearerToken = "Bearer " + jwt;
        Log.d("BEARER TOKEN ", bearerToken);

        PostService postService = CurateClient.getService(PostService.class);
        try{
            Response<JsonObject> response = postService
                    .createPost(bearerToken,
                                postModel.getPostType(),
                                PostConverter.convertPostModelToCuratePost(postModel))
                    .execute();
            Log.d("JSON OBJECT", response.body().toString());
            insertId = response.body().get("postID").getAsJsonObject().get("insertId").getAsInt();
            Log.d("insertId", String.valueOf(insertId));
        } catch (Exception e){
            Log.d("PostRepository Failure", e.getLocalizedMessage());
        }
        return insertId;
    }
}
