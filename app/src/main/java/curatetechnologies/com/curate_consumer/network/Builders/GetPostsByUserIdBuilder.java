package curatetechnologies.com.curate_consumer.network.Builders;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetPostsByUserIdQuery;
import curatetechnologies.com.curate_consumer.graphql.api.fragment.PostsInfoFragment;

public class GetPostsByUserIdBuilder {

    public static List<PostModel> buildPosts(GetPostsByUserIdQuery.Data data) {

        List<PostModel> postModels = new ArrayList<>();

        for (GetPostsByUserIdQuery.Post post : data.posts()) {
            PostModel postModel = buildPost(post);
            postModels.add(postModel);
        }
        return postModels;
    }

    private static PostModel buildPost(GetPostsByUserIdQuery.Post post) {

        PostsInfoFragment postInfo = post.fragments().postsInfoFragment();
        String howLongAgoPosted = calculateHowLongAgoPosted(postInfo.time());

        PostModel postModel = new PostModel(postInfo.id(), postInfo.postType().rawValue(),
                postInfo.restaurant().id(), postInfo.item().id(), postInfo.description(),
                postInfo.rating(), postInfo.numberOfLikes(), postInfo.numberOfSaves(),
                postInfo.imageUrl(), howLongAgoPosted, postInfo.user().id(), postInfo.user().username(),
                postInfo.user().picture(), postInfo.item().name(), postInfo.restaurant().name(),
                null, postInfo.imageUrl());
        return postModel;

    }

    private static String calculateHowLongAgoPosted(String dateString){
        Date d = stringToDate(dateString, "EEE MMM dd yyyy HH:mm:ss");

        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.setTime(d);

        long milliseconds1 = start.getTimeInMillis();
        long milliseconds2 = now.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffMinutes > 60) {
            if (diffHours > 24) {
                return String.valueOf(diffDays) + "d";
            } else {
                return String.valueOf(diffHours) + "h";
            }
        } else {
            if (diffMinutes < 1){
                return "Now";
            } else {
                return String.valueOf(diffMinutes) + "m";
            }
        }
    }

    private static Date stringToDate(String aDate, String aFormat) {

        if (aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

}
