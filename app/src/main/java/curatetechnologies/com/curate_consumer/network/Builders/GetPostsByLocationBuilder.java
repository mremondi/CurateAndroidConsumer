package curatetechnologies.com.curate_consumer.network.Builders;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.graphql.api.LoadFeedQuery;
import curatetechnologies.com.curate_consumer.graphql.api.fragment.PostsInfoFragment;

public class GetPostsByLocationBuilder {

    public static List<PostModel> buildPosts(LoadFeedQuery.Data data) {
        List<PostModel> postModels = new ArrayList<>();
        List<LoadFeedQuery.Post> posts = data.posts();

        for (LoadFeedQuery.Post post : posts) {
            PostModel postModel = buildPost(post);
            postModels.add(postModel);
        }

        return postModels;
    }

    private static PostModel buildPost(LoadFeedQuery.Post post) {

        PostsInfoFragment postInfo = post.fragments().postsInfoFragment();

        PostModel postModel = new PostModel(postInfo.id(), postInfo.postType().rawValue(),
                postInfo.restaurant().id(), postInfo.item().id(), postInfo.description(),
                postInfo.rating(), postInfo.numberOfLikes(), postInfo.numberOfSaves(),
                postInfo.imageUrl(), postInfo.time(), postInfo.user().id(), postInfo.user().username(),
                postInfo.user().picture(), postInfo.item().name(), postInfo.restaurant().name(),
                null, postInfo.imageUrl());
        return postModel;
    }

}
