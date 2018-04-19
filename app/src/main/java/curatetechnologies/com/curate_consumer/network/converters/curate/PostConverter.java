package curatetechnologies.com.curate_consumer.network.converters.curate;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIPost;

/**
 * Created by mremondi on 2/22/18.
 */

public class PostConverter {

    public static PostModel convertCuratePostToPostModel(CurateAPIPost apiPost){
        return new PostModel(apiPost.getPostID(), apiPost.getPostPostType(),
                apiPost.getRestaurantID(), apiPost.getItemID(), apiPost.getPostDescription(),
                Boolean.parseBoolean(apiPost.getPostRating()), apiPost.getPostNumberOfSaves(),
                apiPost.getPostNumberOfSaves(), apiPost.getPostImageURL(),
                calculateHowLongAgoPosted(apiPost.getPostTime()),
                apiPost.getUserID(), "@" + apiPost.getUserUsername(), apiPost.getUserPicture(),
                apiPost.getItemName(), apiPost.getRestaurantName(), apiPost.getDistanceInMiles(), null);
    }

    public static CurateAPIPost convertPostModelToCuratePost(PostModel postModel){
        return new CurateAPIPost(postModel.getId(), postModel.getPostType(),
                postModel.getRestaurantId(), postModel.getItemId(), postModel.getDescription(),
                String.valueOf(postModel.getRating()), postModel.getNumberOfLikes(), postModel.getNumberOfSaves(),
                postModel.getImageURL(), postModel.getTime(), postModel.getUserId(),
                postModel.getUsername(), postModel.getUserPicture(), postModel.getItemName(),
                postModel.getRestaurantName(), postModel.getDistanceInMiles());
    }

    private static String calculateHowLongAgoPosted(String dateString){
        Date d = stringToDate(dateString, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

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

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
