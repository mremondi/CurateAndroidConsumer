package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                apiPost.getPostNumberOfSaves(), apiPost.getPostImageURL(),
                calculateHowLongAgoPosted(apiPost.getPostTime()),
                apiPost.getUserID(), "@" + apiPost.getUserUsername(), apiPost.getUserPicture(),
                apiPost.getItemName(), apiPost.getRestaurantName(), apiPost.getDistanceInMiles());
    }

    private static String calculateHowLongAgoPosted(String dateString){
        Log.d("DATE STRING", dateString);
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
                Log.d("DIFF DAYS", String.valueOf(diffDays) + "d");
                return String.valueOf(diffDays) + "d";
            } else {
                Log.d("DIFF HOURS", String.valueOf(diffDays) + "h");
                return String.valueOf(diffHours) + "h";
            }
        } else {
            if (diffMinutes < 1){
                return "Now";
            } else {
                Log.d("DIFF MINS", String.valueOf(diffDays) + "m");
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
