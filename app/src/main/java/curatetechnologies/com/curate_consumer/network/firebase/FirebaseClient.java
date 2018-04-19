package curatetechnologies.com.curate_consumer.network.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import curatetechnologies.com.curate_consumer.network.firebase.model.FirebaseOrder;

/**
 * Created by mremondi on 3/26/18.
 */

public class FirebaseClient {

    public static Task<Void> pushOrderToFirebase(FirebaseOrder order) {

       DatabaseReference ref = FirebaseDatabase.getInstance()
               .getReference()
               .child("restaurants")
               .child(order.restaurantID)
               .child("new_orders");

        return ref.push().setValue(order);
    }
}
