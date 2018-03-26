package curatetechnologies.com.curate.network.converters.curate;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.config.Utils;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.network.firebase.model.FirebaseItem;
import curatetechnologies.com.curate.network.firebase.model.FirebaseOrder;
import curatetechnologies.com.curate.network.model.CurateAPIOrder;

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
                orderModel.getRestaurantName(), Utils.getCurrentTimeString(), "",
                String.valueOf(orderModel.getUser().getId()), orderModel.getUser().getUsername());
    }

    public static CurateAPIOrder convertOrderModelToCurateModel(OrderModel orderModel){
        JSONArray orderItemInfo = buildOrderItemJSON(orderModel.getOrderItems());

        return new CurateAPIOrder(orderModel.getId(), orderItemInfo, "",
                orderModel.getTotalOrderPrice(), orderModel.getMealTaxRate(), 0.0,
                orderModel.getUser().getId(), orderModel.getRestaurantId());
    }

    private static JSONArray buildOrderItemJSON(List<ItemModel> orderItems){
        JSONArray orderItemArray = new JSONArray();
        for (ItemModel item: orderItems){
            JSONObject jsonItem = new JSONObject();
            try {
                jsonItem.put("Item_Name", item.getName());
                jsonItem.put("Item_Price", item.getPrice());
            } catch (JSONException e){
                Log.d("JSON ERROR", e.getLocalizedMessage());
            }
            orderItemArray.put(jsonItem);
        }
        return orderItemArray;
    }

}