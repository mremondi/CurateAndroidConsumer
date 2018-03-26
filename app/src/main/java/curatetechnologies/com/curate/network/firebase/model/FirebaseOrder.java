package curatetechnologies.com.curate.network.firebase.model;

import java.util.List;

/**
 * Created by mremondi on 3/26/18.
 */

public class FirebaseOrder {

    public String deviceID;
    public String fullName;
    public String instructions;
    public List<FirebaseItem> order_items;
    public String price;
    public String profilePictureURL;
    public String restaurantID;
    public String restaurantName;
    public String startingTime;
    public String timeToCompletion;
    public String userID;
    public String username;

    public FirebaseOrder(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebaseOrder.class)
    }

    public FirebaseOrder(String deviceID, String fullName, String instructions,
                         List<FirebaseItem> order_items, String price, String profilePictureURL,
                         String restaurantID, String restaurantName, String startingTime,
                         String timeToCompletion, String userID, String username) {
        this.deviceID = deviceID;
        this.fullName = fullName;
        this.instructions = instructions;
        this.order_items = order_items;
        this.price = price;
        this.profilePictureURL = profilePictureURL;
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.startingTime = startingTime;
        this.timeToCompletion = timeToCompletion;
        this.userID = userID;
        this.username = username;
    }
}
