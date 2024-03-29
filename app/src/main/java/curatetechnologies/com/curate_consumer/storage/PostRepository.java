package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.converters.curate.PostConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIPost;
import curatetechnologies.com.curate_consumer.network.services.PostService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        PostService postService = CurateClient.getService(PostService.class);

        // IMAGE POST
        if (postModel.getImagePath() != null){
            File file = new File(postModel.getImagePath());

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            try {
                Log.d("FILE SIZE", String.valueOf(reqFile.contentLength()));
            } catch (IOException e){

            }
            MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", "myFile", reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            HashMap<String, RequestBody> map = buildPostMap(postModel);


            try{
                Response<JsonObject> response = postService
                        .createImagePost(bearerToken,
                                map,
                                body,
                                postModel.getPostType())
                        .execute();
                Log.d("HERE", "ABOVE");
                Log.d("JSON OBJECT", response.body().toString());
                Log.d("HERE", "BELOW");
                insertId = response.body().get("postID").getAsJsonObject().get("insertId").getAsInt();
                Log.d("insertId", String.valueOf(insertId));
            } catch (Exception e){
                Log.d("PostRepository Failure", e.getLocalizedMessage());
            }

            return insertId;
        }
        // RATING OR ORDER POST
        else {
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

    private HashMap buildPostMap(PostModel postModel){
        HashMap<String, RequestBody> map = new HashMap<>();

        RequestBody id = RequestBody.create(okhttp3.MultipartBody.FORM, "0");
        RequestBody userId = RequestBody.create(MultipartBody.FORM, "" + postModel.getUserId());
        RequestBody restaurantId = RequestBody.create(okhttp3.MultipartBody.FORM, "" + postModel.getRestaurantId());
        RequestBody itemId = RequestBody.create(okhttp3.MultipartBody.FORM, "" + postModel.getItemId());
        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, postModel.getDescription());
        RequestBody rating = RequestBody.create(okhttp3.MultipartBody.FORM, "" + postModel.getRating().toString());

        map.put("Post_ID", id);
        map.put("User_ID", userId);
        map.put("Restaurant_ID", restaurantId);
        map.put("Item_ID", itemId);
        map.put("Post_Description", description);
        map.put("Post_Rating", rating);

        return map;
    }
}
