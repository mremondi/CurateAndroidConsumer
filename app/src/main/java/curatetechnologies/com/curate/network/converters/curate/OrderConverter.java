package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.network.firebase.model.FirebaseItem;
import curatetechnologies.com.curate.network.firebase.model.FirebaseOrder;

/**
 * Created by mremondi on 3/26/18.
 */

public class OrderConverter {

    public static FirebaseOrder convertOrderModelToFirebaseOrder(OrderModel orderModel){

        List<FirebaseItem> orderItems = new ArrayList<>();
        for (ItemModel item: orderModel.getOrderItems()){
            orderItems.add(ItemConverter.convertItemModelToFirebaseItem(item));
        }

        return new FirebaseOrder(orderModel.getDeviceId(), orderModel.getUser().getFullName(),
                orderModel.getInstructions(), orderItems, String.valueOf(orderModel.getTotalOrderPrice()),
                orderModel.getUser().getProfilePictureURL(), String.valueOf(orderModel.getRestaurantId()),
                orderModel.getRestaurantName(), getCurrentTime(), "",
                String.valueOf(orderModel.getUser().getId()), orderModel.getUser().getUsername());
    }

    private static String getCurrentTime() {
        // TODO:
        return "";
    }
}