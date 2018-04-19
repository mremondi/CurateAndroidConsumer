package curatetechnologies.com.curate_consumer.network.firebase.model;

/**
 * Created by mremondi on 3/26/18.
 */

public class FirebaseItem {

    public String itemID;
    public String itemName;
    public String itemPrice;

    public FirebaseItem() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebaseItem.class)
    }

    public FirebaseItem(String itemID, String itemName, String itemPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
